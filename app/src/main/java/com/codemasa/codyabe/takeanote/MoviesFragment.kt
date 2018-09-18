package com.codemasa.codyabe.takeanote

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MoviesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)



        return view
    }

    companion object {
        fun newInstance(): MoviesFragment = MoviesFragment()
    }
}