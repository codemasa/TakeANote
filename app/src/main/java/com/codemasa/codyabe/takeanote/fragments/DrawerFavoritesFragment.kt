package com.codemasa.codyabe.takeanote.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codemasa.codyabe.takeanote.*
import com.codemasa.codyabe.takeanote.adapters.MovieAdapter
import com.codemasa.codyabe.takeanote.adapters.TVShowAdapter
import com.codemasa.codyabe.takeanote.helpers.ItemTouchHelperAdapter
import com.codemasa.codyabe.takeanote.helpers.ItemTouchHelperCallback
import com.codemasa.codyabe.takeanote.listeners.OnStartDragListener
import com.codemasa.codyabe.takeanote.model.DatabaseHelper
import com.codemasa.codyabe.takeanote.model.Movie
import com.codemasa.codyabe.takeanote.model.TVShow

class DrawerFavoritesFragment : Fragment(), OnStartDragListener {


    lateinit var movieListView: RecyclerView
    lateinit var tvShowListView: RecyclerView
    lateinit var movieTouchHelper: ItemTouchHelper
    lateinit var tvShowTouchHelper: ItemTouchHelper
    lateinit var movieData: MutableList<Movie>
    lateinit var tvData: MutableList<TVShow>
    lateinit var movieDataList: MovieAdapter
    lateinit var tvDataList: TVShowAdapter
    lateinit var db: DatabaseHelper


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_drawer_favorites, container, false)

        movieListView = view.findViewById(R.id.movie_display_list_favorite)
        movieListView.layoutManager = LinearLayoutManager(context)
        tvShowListView = view.findViewById(R.id.tv_show_list_view_favorite)
        tvShowListView.layoutManager = LinearLayoutManager(context)


        db = DatabaseHelper(context)

        movieData = db.readFavoriteMovies()
        tvData = db.readFavoriteTVShows()

        movieDataList = MovieAdapter(context, movieData as ArrayList<Movie>, this)
        tvDataList = TVShowAdapter(context, tvData as ArrayList<TVShow>, this)

        movieListView.adapter = movieDataList
        tvShowListView.adapter = tvDataList

        val movieCallback: ItemTouchHelperCallback = ItemTouchHelperCallback(movieListView.adapter as ItemTouchHelperAdapter)
        val tvCallback: ItemTouchHelperCallback = ItemTouchHelperCallback(tvShowListView.adapter as ItemTouchHelperAdapter)

        movieTouchHelper = ItemTouchHelper(movieCallback)
        tvShowTouchHelper = ItemTouchHelper(tvCallback)

        movieTouchHelper.attachToRecyclerView(movieListView)
        movieTouchHelper.attachToRecyclerView(tvShowListView)




        return view
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        movieTouchHelper.startDrag(viewHolder)
        tvShowTouchHelper.startDrag(viewHolder)
    }


    companion object {
        fun newInstance(): DrawerFavoritesFragment = DrawerFavoritesFragment()
    }
}