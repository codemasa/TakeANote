package com.codemasa.codyabe.takeanote

import android.content.Context
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
        }

        titleTextView.text = movie.title
        directorTextView.text = movie.director
        yearTextView.text = movie.releaseDate.toString()

        Picasso.get().load("replace").placeholder(R.mipmap.ic_launcher).into(thumbnail)



        return rowView
    }


}