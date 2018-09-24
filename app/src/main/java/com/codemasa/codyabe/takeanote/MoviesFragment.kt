package com.codemasa.codyabe.takeanote

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.*

class MoviesFragment : Fragment() {

    lateinit var movieListView : ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)
        movieListView = view.findViewById<ListView>(R.id.movie_display_list)
        val db = DatabaseHelper(context)
        var data = db.readMovies()
        var dataList : AdapterMovie = AdapterMovie(context, data as ArrayList<Movie>)
        movieListView.adapter = dataList



        return view
    }

    companion object {
        fun newInstance(): MoviesFragment = MoviesFragment()
    }


}
