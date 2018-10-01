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
    lateinit var data : MutableList<Movie>
    lateinit var dataList: MovieAdapter
    lateinit var db : DatabaseHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)
        movieListView = view.findViewById(R.id.movie_display_list)
        db = DatabaseHelper(context)
        data = db.readMovies()
        dataList = MovieAdapter(context, data as ArrayList<Movie>, this)
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

    fun onSave(){
        data = db.readMovies()
        dataList = MovieAdapter(context, data as ArrayList<Movie>, this)
        movieListView.adapter = dataList
    }

    companion object {
        fun newInstance(): MoviesFragment = MoviesFragment()
    }
}
