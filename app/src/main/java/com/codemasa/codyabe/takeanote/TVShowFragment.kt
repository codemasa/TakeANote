package com.codemasa.codyabe.takeanote

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

class TVShowFragment : Fragment() {

    lateinit var tvShowListView : RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tv, container, false)

        tvShowListView = view.findViewById(R.id.tv_show_display_list)
        val db = DatabaseHelper(context)
        var data = db.readTVShows()
        var dataList : TVShowAdapter = TVShowAdapter(context, data as ArrayList<TVShow>)
        tvShowListView.adapter = dataList
        tvShowListView.layoutManager = LinearLayoutManager(context)
        return view
    }

    companion object {
        fun newInstance(): TVShowFragment = TVShowFragment()
    }
}