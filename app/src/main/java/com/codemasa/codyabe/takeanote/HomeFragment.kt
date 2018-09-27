package com.codemasa.codyabe.takeanote

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView

class HomeFragment : Fragment() {

    lateinit var movieListView : RecyclerView
    lateinit var tvShowListView : RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        movieListView = view.findViewById(R.id.movie_display_list_home)
        movieListView.layoutManager = LinearLayoutManager(context)
        tvShowListView = view.findViewById(R.id.tv_show_list_view_home)
        tvShowListView.layoutManager = LinearLayoutManager(context)


        val db = DatabaseHelper(context)

        val movieData = db.readMovies()
        val tvData = db.readTVShows()

        val movieDataList : MovieAdapter = MovieAdapter(context, movieData as ArrayList<Movie>)
        val tvDataList : TVShowAdapter = TVShowAdapter(context, tvData as ArrayList<TVShow>)

        movieListView.adapter = movieDataList
        tvShowListView.adapter = tvDataList


        return view
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}