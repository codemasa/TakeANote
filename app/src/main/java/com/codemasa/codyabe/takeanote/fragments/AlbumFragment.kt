package com.codemasa.codyabe.takeanote.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.codemasa.codyabe.takeanote.model.DatabaseHelper
import com.codemasa.codyabe.takeanote.model.Movie
import com.codemasa.codyabe.takeanote.R
import com.codemasa.codyabe.takeanote.adapters.AlbumAdapter

class AlbumFragment : Fragment() {

    lateinit var albumListView : ListView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_album, container, false)

        albumListView = view.findViewById(R.id.album_display_list)
        val db = DatabaseHelper(context)
        var data = db.readAlbums()
        var dataList : AlbumAdapter = AlbumAdapter(context, data as ArrayList<Movie>)
        albumListView.adapter = dataList

        return view
    }

    companion object {
        fun newInstance(): AlbumFragment = AlbumFragment()
    }
}