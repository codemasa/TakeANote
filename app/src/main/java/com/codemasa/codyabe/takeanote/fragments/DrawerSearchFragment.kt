package com.codemasa.codyabe.takeanote.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codemasa.codyabe.takeanote.R

class DrawerSearchFragment : Fragment() {

    companion object {
        fun newInstance(): DrawerSearchFragment = DrawerSearchFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_drawer_search, container, false)






        return view
    }
}