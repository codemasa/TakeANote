package com.codemasa.codyabe.takeanote

import android.content.ClipData
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

class TVShowFragment : Fragment(), OnStartDragListener {

    lateinit var tvShowListView : RecyclerView
    lateinit var tvShowTouchHelper: ItemTouchHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tv, container, false)

        tvShowListView = view.findViewById(R.id.tv_show_display_list)
        val db = DatabaseHelper(context)
        var data = db.readTVShows()
        var dataList : TVShowAdapter = TVShowAdapter(context, data as ArrayList<TVShow>, this)
        tvShowListView.adapter = dataList
        tvShowListView.layoutManager = LinearLayoutManager(context)
        val callback : ItemTouchHelperCallback = ItemTouchHelperCallback(tvShowListView.adapter as ItemTouchHelperAdapter)
        tvShowTouchHelper = ItemTouchHelper(callback)
        tvShowTouchHelper.attachToRecyclerView(tvShowListView)


        return view
    }
    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        tvShowTouchHelper.startDrag(viewHolder)
    }

    companion object {
        fun newInstance(): TVShowFragment = TVShowFragment()
    }
}