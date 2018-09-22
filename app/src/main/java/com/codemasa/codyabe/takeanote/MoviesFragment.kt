package com.codemasa.codyabe.takeanote

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class MoviesFragment : Fragment() {

    lateinit var movieListView : ListView
    lateinit var editDoneButton : ToggleButton
    lateinit var rearrangeButton : Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)
        movieListView = view.findViewById<ListView>(R.id.movie_display_list)
        val db = DatabaseHelper(context)
        var data = db.readMovies()
        var dataList : AdapterMovie = AdapterMovie(context, data as ArrayList<Movie>)
        movieListView.adapter = dataList
        editDoneButton = this.parentFragment.activity.findViewById(R.id.list_edit_button)

        view.setOnCreateContextMenuListener { contextMenu, view, contextMenuInfo ->
            activity.menuInflater.inflate(R.menu.more_options_menu, contextMenu)
        }

        return view
    }

    companion object {
        fun newInstance(): MoviesFragment = MoviesFragment()
    }


}
