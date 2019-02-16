package com.codemasa.codyabe.takeanote.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.codemasa.codyabe.takeanote.R

class DrawerProfileFragment : Fragment() {

    companion object {
        fun newInstance(): DrawerProfileFragment = DrawerProfileFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_drawer_profile, container, false)
        val text : TextView = view.findViewById(R.id.profile)
        text.text = "Profile"



        return view
    }
}