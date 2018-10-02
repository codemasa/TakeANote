package com.codemasa.codyabe.takeanote

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView

class HomeFragment : Fragment(), OnStartDragListener {


    lateinit var movieListView : RecyclerView
    lateinit var tvShowListView : RecyclerView
    lateinit var movieTouchHelper: ItemTouchHelper
    lateinit var tvShowTouchHelper : ItemTouchHelper
    lateinit var movieData : MutableList<Movie>
    lateinit var tvData : MutableList<TVShow>
    lateinit var movieDataList: MovieAdapter
    lateinit var tvDataList: TVShowAdapter
    lateinit var db : DatabaseHelper



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        movieListView = view.findViewById(R.id.movie_display_list_home)
        movieListView.layoutManager = LinearLayoutManager(context)
        tvShowListView = view.findViewById(R.id.tv_show_list_view_home)
        tvShowListView.layoutManager = LinearLayoutManager(context)


        db = DatabaseHelper(context)

        movieData = db.readMovies()
        tvData = db.readTVShows()

        movieDataList = MovieAdapter(context, movieData as ArrayList<Movie>,this)
        tvDataList = TVShowAdapter(context, tvData as ArrayList<TVShow>, this)

        movieListView.adapter = movieDataList
        tvShowListView.adapter = tvDataList

        val movieCallback : ItemTouchHelperCallback = ItemTouchHelperCallback(movieListView.adapter as ItemTouchHelperAdapter)
        val tvCallback : ItemTouchHelperCallback = ItemTouchHelperCallback(tvShowListView.adapter as ItemTouchHelperAdapter)

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

    fun onSave() {
        movieData = db.readMovies()
        tvData = db.readTVShows()

        movieDataList = MovieAdapter(context, movieData as ArrayList<Movie>, this)
        tvDataList = TVShowAdapter(context, tvData as ArrayList<TVShow>, this)

        movieListView.adapter = movieDataList
        tvShowListView.adapter = tvDataList

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        db = DatabaseHelper(context)
        when(resultData!!.getStringExtra("category")) {
            "movie" -> {
                movieData = db.readMovies()
                movieDataList = MovieAdapter(context, movieData as ArrayList<Movie>, this)
                movieDataList.onActivityResult(requestCode, resultCode, resultData)
                movieData = db.readMovies()
                movieDataList = MovieAdapter(context, movieData as ArrayList<Movie>, this)
                movieListView.adapter = movieDataList
                Log.d("Main", "ON ACTIVITY RESULT MOVIES FRAGMENT")
            }
            "tvShow" -> {
                tvData = db.readTVShows()
                tvDataList = TVShowAdapter(context, tvData as ArrayList<TVShow>, this)
                tvDataList.onActivityResult(requestCode, resultCode, resultData)
                tvData = db.readTVShows()
                tvDataList = TVShowAdapter(context, tvData as ArrayList<TVShow>, this)
                tvShowListView.adapter = tvDataList
                Log.d("Main", "ON ACTIVITY RESULT MOVIES FRAGMENT")
            }
        }

    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}