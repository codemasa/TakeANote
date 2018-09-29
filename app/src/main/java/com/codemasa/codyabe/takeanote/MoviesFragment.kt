package com.codemasa.codyabe.takeanote

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.*
import android.widget.*

class MoviesFragment : Fragment(), OnStartDragListener {

    lateinit var movieListView : RecyclerView
    lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)
        movieListView = view.findViewById(R.id.movie_display_list)
        val db = DatabaseHelper(context)
        var data = db.readMovies()
        var dataList : MovieAdapter = MovieAdapter(context, data as ArrayList<Movie>, this)
        movieListView.adapter = dataList
        movieListView.layoutManager = LinearLayoutManager(context)
        val callback : ItemTouchHelperCallback = ItemTouchHelperCallback(movieListView.adapter as ItemTouchHelperAdapter)
        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(movieListView)


        return view
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }
    override fun onResume() {
        super.onResume()
        movieListView.refreshDrawableState()
    }
    companion object {
        fun newInstance(): MoviesFragment = MoviesFragment()
    }


}
