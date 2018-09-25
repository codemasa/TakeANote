package com.codemasa.codyabe.takeanote

import android.content.Context
import android.util.Log
import android.view.*
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso


class MovieAdapter(private val context: Context,
                   private val dataSource: ArrayList<Movie>) : BaseAdapter(){

    internal lateinit var requestQueue : RequestQueue

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.list_item_fragment_movie, parent, false)

        val titleTextView : TextView = rowView.findViewById(R.id.category_list_title)
        val directorTextView : TextView = rowView.findViewById(R.id.category_list_subtitle)
        val yearTextView : TextView = rowView.findViewById(R.id.category_list_detail)
        val thumbnail : ImageView = rowView.findViewById(R.id.category_list_thumbnail)

        val movie : Movie = getItem(position) as Movie

        val rearrangeButton : Button
        rearrangeButton = rowView.findViewById(R.id.rearrange_button)
        rearrangeButton.setOnClickListener {
            Toast.makeText(context, "Ready to rearrange", Toast.LENGTH_LONG).show()

            true
        }
        rearrangeButton.setOnDragListener { view, dragEvent ->

            true
        }

        val vertMoreButton : Button
        vertMoreButton = rowView.findViewById(R.id.more_options_button)

        vertMoreButton.setOnClickListener {
            Toast.makeText(context, "More Options", Toast.LENGTH_LONG).show()
            showMoreOptionsMenu(it,movie)
        }


        titleTextView.text = movie.title
        directorTextView.text = movie.director
        yearTextView.text = movie.releaseDate.toString()
        val APIKey = BuildConfig.ApiKey
        val imdbURL = "http://omdbapi.com/?t=" + movie.title +"&apikey=" + APIKey
        var APIResponse : String = ""
        requestQueue = Volley.newRequestQueue(context)
        val APIRequest = JsonObjectRequest(Request.Method.GET, imdbURL, null,
                Response.Listener {response ->  
                    APIResponse = response.getString("imdbID")
                    Picasso.get().load("http://img.omdbapi.com/?i=" + APIResponse +"&h=600&apikey=" + APIKey).into(thumbnail)

                },
                Response.ErrorListener {error ->

                }
        )
        requestQueue.add(APIRequest)



        return rowView
    }

    fun showMoreOptionsMenu(view : View, movie:Movie){
        val moreOptionsMenu : PopupMenu = PopupMenu(context, view)

        moreOptionsMenu.setOnMenuItemClickListener{item ->
            val db = DatabaseHelper(context)
            when(item.itemId) {
                R.id.popup_delete -> {
                    Toast.makeText(context, "Delete", Toast.LENGTH_LONG).show()
                    db.deleteMovie(movie.id)
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


}