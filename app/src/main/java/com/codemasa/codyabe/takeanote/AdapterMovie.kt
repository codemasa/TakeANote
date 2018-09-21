package com.codemasa.codyabe.takeanote

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
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

        titleTextView.text = movie.title
        directorTextView.text = movie.director
        yearTextView.text = movie.releaseDate.toString()

        Picasso.get().load("replace").placeholder(R.mipmap.ic_launcher).into(thumbnail)
        return rowView
    }


}