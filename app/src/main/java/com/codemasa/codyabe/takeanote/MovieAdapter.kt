package com.codemasa.codyabe.takeanote

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.support.v4.app.ShareCompat
import android.support.v4.view.MenuItemCompat
import android.support.v4.view.MotionEventCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import java.net.URI
import java.util.*
import android.widget.ShareActionProvider
import kotlin.collections.ArrayList


class MovieAdapter(private val context: Context,
                   private val dataSource: ArrayList<Movie>, dragListener: OnStartDragListener) : RecyclerView.Adapter<MovieAdapter.ViewHolder>(), ItemTouchHelperAdapter{

    internal lateinit var movie: Movie
    internal var shareActionProvider: ShareActionProvider? = null
    internal var dragStartListener : OnStartDragListener
    init {
        this.dragStartListener = dragListener
    }


    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(dataSource, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(dataSource, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_item_fragment_movie, parent, false)


        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return this.dataSource.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        movie = getItem(position) as Movie

        val favoriteStar : ImageView = holder.itemView.findViewById(R.id.favorite_movie)
        if(movie.favorite){
            favoriteStar.visibility = View.VISIBLE
        }
        else{
            favoriteStar.visibility = View.GONE
        }

        val rearrangeButton : ImageView
        rearrangeButton = holder.itemView.findViewById(R.id.rearrange_button)

        rearrangeButton.setOnTouchListener { view, motionEvent ->
            object : View.OnTouchListener{
                override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
                    if(motionEvent.action == MotionEvent.ACTION_DOWN){
                        dragStartListener.onStartDrag(holder)
                    }

                    return false
                }
            }
            false
        }


        val vertMoreButton : Button
        vertMoreButton = holder.itemView.findViewById(R.id.more_options_button)

        vertMoreButton.setOnClickListener {
            Toast.makeText(context, "More Options", Toast.LENGTH_LONG).show()
            showMoreOptionsMenu(it,movie, position)
        }

        holder.itemView.setOnClickListener {view ->
            val intent : Intent = NoteTakingActivity.newIntent(context)
            val activity : MainActivity = context as MainActivity
            view?.startAnimation(AnimationUtils.loadAnimation(context, R.anim.click_item_on_list))
            intent.putExtra("category", "movie")
            intent.putExtra("title", movie.title)
            context.startActivity(intent)
            activity.overridePendingTransition(R.anim.left_to_right_enter, R.anim.left_to_right_exit)
        }

        holder.titleTextView.text = movie.title
        holder.directorTextView.text = movie.director
        holder.yearTextView.text = movie.releaseDate.toString()
        val APIKey = BuildConfig.ApiKey
        val imdbURL = "http://omdbapi.com/?t=" + movie.title +"&apikey=" + APIKey
        var APIResponse : String = ""
        requestQueue = Volley.newRequestQueue(context)
        if(movie.imageURL == "") {
            val APIRequest = JsonObjectRequest(Request.Method.GET, imdbURL, null,
                    Response.Listener { response ->
                        try {
                            APIResponse = response.getString("imdbID")
                            Picasso.get().load("http://img.omdbapi.com/?i=" + APIResponse + "&h=600&apikey=" + APIKey).into(holder.thumbnail)
                        }
                        catch (e : JSONException){
                            holder.thumbnail.setBackgroundColor(context.getColor(R.color.ripple_material_light))
                            holder.thumbnail.setImageResource(R.drawable.ic_add)
                        }

                    },
                    Response.ErrorListener { error ->
                    }
            )
            requestQueue.add    (APIRequest)
        }
        else{
            holder.thumbnail.setImageURI(Uri.parse(movie.imageURL))
        }


    }

    internal lateinit var requestQueue : RequestQueue

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater



    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getItem(position: Int): Any {
        return dataSource[position]
    }


    fun showMoreOptionsMenu(view : View, movie:Movie, position: Int){
        val moreOptionsMenu : PopupMenu = PopupMenu(context, view)

        moreOptionsMenu.setOnMenuItemClickListener{item ->
            val db = DatabaseHelper(context)
            when(item.itemId) {
                R.id.popup_favorite -> {
                    db.markAsFavorite(movie)
                    movie.favorite = !movie.favorite
                    this.notifyDataSetChanged()
                    true
                }
                R.id.popup_delete -> {
                    val movie = dataSource[position]
                    db.deleteMovie(movie.id)
                    dataSource.remove(movie)
                    this.notifyDataSetChanged()
                    true
                }
                R.id.popup_edit -> {
                    val intent = NoteEditActivity.newIntent(context)
                    intent.putExtra("id", movie.id)
                    intent.putExtra("title", movie.title)
                    intent.putExtra("director", movie.director)
                    intent.putExtra("year", movie.releaseDate)
                    intent.putExtra("imageURL", movie.imageURL)
                    intent.putExtra("type", "edit")
                    (context as MainActivity).startActivityForResult(intent, 2)



                    true
                }
                R.id.popup_share-> {
                    val movie = dataSource[position]
                    shareActionProvider = item.actionProvider as? ShareActionProvider
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    val noteList = db.readNotes("movie", movie.title)
                    val noteListString : StringBuilder = StringBuilder()
                    for(note in noteList){
                        noteListString.append(note.noteBody+"\n")

                    }
                    val extraText = noteListString.toString()
                    shareIntent.setType("text/plain")
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, movie.title)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, extraText)
                    context.startActivity(Intent.createChooser(shareIntent,"Share Via..."))

                    true
                }
                else -> false
            }
        }

        moreOptionsMenu.inflate(R.menu.more_options_menu)
        try {
            val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
            fieldMPopup.isAccessible = true
            val mPopup = fieldMPopup.get(moreOptionsMenu)
            mPopup.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(mPopup,true)
        }catch (e: Exception){
            Log.e("Main", "Error showing menu icons.",e)
        } finally {
            moreOptionsMenu.show()
        }

    }

    fun onActivityResult(resultCode: Int, requestCode: Int, data: Intent?){
        Log.d("Main", "ON ACTIVITY RESULT MOVIES ADAPTER")
        val db = DatabaseHelper(context)
        db.updateMovies(data!!.getStringExtra("title"), data.getStringExtra("director"), data.getIntExtra("year",0), data.getStringExtra("imageURL"), data.getIntExtra("id",0))
        notifyDataSetChanged()
    }

    public class ViewHolder : RecyclerView.ViewHolder {

        internal var titleTextView : TextView
        internal var directorTextView : TextView
        internal var yearTextView : TextView
        internal var thumbnail : ImageView

        constructor(view: View) : super(view) {
            titleTextView  = view.findViewById(R.id.category_list_title)
            directorTextView = view.findViewById(R.id.category_list_subtitle)
            yearTextView  = view.findViewById(R.id.category_list_detail)
            thumbnail  = view.findViewById(R.id.category_list_thumbnail)
        }


    }


}