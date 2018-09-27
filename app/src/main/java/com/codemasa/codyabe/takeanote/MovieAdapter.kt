package com.codemasa.codyabe.takeanote

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.Image
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


class MovieAdapter(private val context: Context,
                   private val dataSource: ArrayList<Movie>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_item_fragment_movie, parent, false)


        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return this.dataSource.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie : Movie = getItem(position) as Movie

        val favoriteStar : ImageView = holder.itemView.findViewById(R.id.favorite_movie)
        if(movie.favorite){
            favoriteStar.visibility = View.VISIBLE
        }
        else{
            favoriteStar.visibility = View.GONE
        }

        val rearrangeButton : Button
        rearrangeButton = holder.itemView.findViewById(R.id.rearrange_button)
        rearrangeButton.setOnClickListener {
            Toast.makeText(context, "Ready to rearrange", Toast.LENGTH_LONG).show()

            true
        }
        rearrangeButton.setOnDragListener { view, dragEvent ->

            true
        }

        val vertMoreButton : Button
        vertMoreButton = holder.itemView.findViewById(R.id.more_options_button)

        vertMoreButton.setOnClickListener {
            Toast.makeText(context, "More Options", Toast.LENGTH_LONG).show()
            showMoreOptionsMenu(it,movie)
        }

        holder.itemView.setOnClickListener {view ->
            val intent : Intent = NoteTakingActivity.newIntent(context)
            val activity : MainActivity = context as MainActivity
            view?.startAnimation(AnimationUtils.loadAnimation(context, R.anim.click_item_on_list))
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
        val APIRequest = JsonObjectRequest(Request.Method.GET, imdbURL, null,
                Response.Listener {response ->
                    APIResponse = response.getString("imdbID")
                    Picasso.get().load("http://img.omdbapi.com/?i=" + APIResponse +"&h=600&apikey=" + APIKey).into(holder.thumbnail)

                },
                Response.ErrorListener {error ->

                }
        )
        requestQueue.add(APIRequest)


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


    fun showMoreOptionsMenu(view : View, movie:Movie){
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
                    db.deleteMovie(movie.id)
                    dataSource.remove(movie)
                    this.notifyDataSetChanged()
                    true
                }
                R.id.popup_edit -> {
                    val intent = NoteEditActivity.newIntent(context)
                    intent.putExtra("title", movie.title)
                    intent.putExtra("director", movie.director)
                    intent.putExtra("year", movie.releaseDate)
                    intent.putExtra("type", "edit")
                    context.startActivity(intent)


                    true
                }
                R.id.popup_share-> {

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

    public class ViewHolder : RecyclerView.ViewHolder {

        internal lateinit var titleTextView : TextView
        internal lateinit var directorTextView : TextView
        internal lateinit var yearTextView : TextView
        internal lateinit var thumbnail : ImageView

        constructor(view: View) : super(view) {
            titleTextView  = view.findViewById(R.id.category_list_title)
            directorTextView = view.findViewById(R.id.category_list_subtitle)
            yearTextView  = view.findViewById(R.id.category_list_detail)
            thumbnail  = view.findViewById(R.id.category_list_thumbnail)
        }


    }


}