package com.codemasa.codyabe.takeanote

import android.content.Context
import android.util.Log
import android.view.*
import android.widget.*
import com.squareup.picasso.Picasso



class AdapterMovie(private val context: Context,
                   private val dataSource: ArrayList<Movie>) : BaseAdapter(){

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
        val rowView = inflater.inflate(R.layout.list_item_fragment, parent, false)

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
            showMoreOptionsMenu(it)
        }


        titleTextView.text = movie.title
        directorTextView.text = movie.director
        yearTextView.text = movie.releaseDate.toString()

        Picasso.get().load("replace").placeholder(R.mipmap.ic_launcher).into(thumbnail)



        return rowView
    }

    fun showMoreOptionsMenu(view : View){
        val moreOptionsMenu : PopupMenu = PopupMenu(context, view)

        moreOptionsMenu.setOnMenuItemClickListener{item ->
            when(item.itemId) {
                R.id.popup_delete -> {
                    Toast.makeText(context, "Delete", Toast.LENGTH_LONG).show()
                    true
                }
                R.id.popup_edit -> {
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