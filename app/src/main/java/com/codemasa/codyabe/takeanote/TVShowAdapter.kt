package com.codemasa.codyabe.takeanote

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import java.util.*


class TVShowAdapter(private val context: Context,
                    private val dataSource: ArrayList<TVShow>, dragStartListener : OnStartDragListener) : RecyclerView.Adapter<TVShowAdapter.ViewHolder>(), ItemTouchHelperAdapter{


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


    internal lateinit var tvShow: TVShow
    internal var shareActionProvider : ShareActionProvider? = null
    val dragStartListener : OnStartDragListener
    init{
        this.dragStartListener = dragStartListener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_item_fragment_tv_show, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.dataSource.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        tvShow = getItem(position) as TVShow

        val favoriteStar : ImageView = holder.itemView.findViewById(R.id.favorite_tv_show)
        if(tvShow.favorite){
            favoriteStar.visibility = View.VISIBLE
        }
        else{
            favoriteStar.visibility = View.GONE
        }

        val rearrangeButton : Button
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
            showMoreOptionsMenu(it,tvShow, position)
        }

        holder.itemView.setOnClickListener { view ->
            Toast.makeText(context, "Here", Toast.LENGTH_SHORT).show()
            val intent : Intent = NoteTakingActivity.newIntent(context)
            val activity : MainActivity = context as MainActivity
            view?.startAnimation(AnimationUtils.loadAnimation(context, R.anim.click_item_on_list))
            intent.putExtra("category", "tvShow")
            intent.putExtra("title", tvShow.title)
            context.startActivity(intent)
            activity.overridePendingTransition(R.anim.left_to_right_enter, R.anim.left_to_right_exit)

        }


        holder.titleTextView.text = tvShow.title
        holder.seasonTextView.text = context.getString(R.string.season).format(tvShow.season)
        holder.yearTextView.text = tvShow.releaseDate.toString()
        if(tvShow.imageURL == "") {
            val APIKey = BuildConfig.ApiKey
            val imdbURL = "http://omdbapi.com/?t=" + tvShow.title + "&apikey=" + APIKey
            var APIResponse: String = ""
            requestQueue = Volley.newRequestQueue(context)
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
            requestQueue.add(APIRequest)
        }else {
            holder.thumbnail.setImageURI(Uri.parse(tvShow.imageURL))
        }


    }


    internal lateinit var requestQueue : RequestQueue

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }



    fun showMoreOptionsMenu(view : View, tvShow: TVShow, position: Int){
        val moreOptionsMenu : PopupMenu = PopupMenu(context, view)

        moreOptionsMenu.setOnMenuItemClickListener{item ->
            val db = DatabaseHelper(context)
            when(item.itemId) {
                R.id.popup_favorite -> {
                    db.markAsFavorite(tvShow)
                    tvShow.favorite = !tvShow.favorite
                    this.notifyDataSetChanged()

                    true
                }
                R.id.popup_delete -> {
                    val tvShow = dataSource[position]
                    dataSource.remove(tvShow)
                    db.deleteTVShow(tvShow.id)
                    this.notifyDataSetChanged()
                    true
                }
                R.id.popup_edit -> {
                    val intent = NoteEditActivity.newIntent(context)
                    intent.putExtra("id", tvShow.id)
                    intent.putExtra("title", tvShow.title)
                    intent.putExtra("director", tvShow.season)
                    intent.putExtra("year", tvShow.releaseDate)
                    intent.putExtra("imageURL", tvShow.imageURL)
                    intent.putExtra("type", "edit")
                    (context as MainActivity).startActivityForResult(intent,2)

                    true
                }
                R.id.popup_share-> {
                    val tvShow = dataSource[position]
                    shareActionProvider = item.actionProvider as? ShareActionProvider
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    val noteList = db.readNotes("movie", tvShow.title)
                    val noteListString : StringBuilder = StringBuilder()
                    for(note in noteList){
                        noteListString.append(note.noteBody+"\n")

                    }
                    val extraText = noteListString.toString()
                    shareIntent.setType("text/plain")
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, tvShow.title)
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
        db.updateTVShow(data!!.getStringExtra("title"), data.getIntExtra("season",0), data.getIntExtra("year",0), data.getStringExtra("imageURL"), data.getIntExtra("id",0))
        notifyDataSetChanged()
    }

    public class ViewHolder : RecyclerView.ViewHolder {

        internal var titleTextView : TextView
        internal var seasonTextView : TextView
        internal var yearTextView : TextView
        internal var thumbnail : ImageView

        constructor(view: View) : super(view) {
            titleTextView = view.findViewById(R.id.category_list_title)
            seasonTextView = view.findViewById(R.id.category_list_subtitle)
            yearTextView = view.findViewById(R.id.category_list_detail)
            thumbnail = view.findViewById(R.id.category_list_thumbnail)
        }


    }


}