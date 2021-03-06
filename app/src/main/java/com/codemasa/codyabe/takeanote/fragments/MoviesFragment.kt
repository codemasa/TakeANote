package com.codemasa.codyabe.takeanote.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.*
import com.codemasa.codyabe.takeanote.*
import com.codemasa.codyabe.takeanote.adapters.MovieAdapter
import com.codemasa.codyabe.takeanote.helpers.ItemTouchHelperAdapter
import com.codemasa.codyabe.takeanote.helpers.ItemTouchHelperCallback
import com.codemasa.codyabe.takeanote.listeners.OnStartDragListener
import com.codemasa.codyabe.takeanote.model.DatabaseHelper
import com.codemasa.codyabe.takeanote.model.Movie

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        db = DatabaseHelper(context)
        data = db.readMovies()
        dataList = MovieAdapter(context, data as ArrayList<Movie>, this)
        dataList.onActivityResult(requestCode,resultCode,resultData)
        data = db.readMovies()
        dataList = MovieAdapter(context, data as ArrayList<Movie>, this)
        movieListView.adapter = dataList
        Log.d("Main", "ON ACTIVITY RESULT MOVIES FRAGMENT")
    }
    companion object {
        fun newInstance(): MoviesFragment = MoviesFragment()
    }
}
