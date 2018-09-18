package com.codemasa.codyabe.takeanote

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.w3c.dom.Text

class DrawerBacklogFragment : Fragment() {

    companion object {
        fun newInstance(): DrawerBacklogFragment = DrawerBacklogFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_drawer_profile, container, false)
        val text : TextView = view.findViewById(R.id.profile)
        text.text = "Backlog"



        return view
    }
}