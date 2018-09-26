package com.codemasa.codyabe.takeanote

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.*

class MoviesFragment : Fragment() {

    lateinit var movieListView : ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)
        movieListView = view.findViewById<ListView>(R.id.movie_display_list)
        val db = DatabaseHelper(context)
        var data = db.readMovies()
        var dataList : MovieAdapter = MovieAdapter(context, data as ArrayList<Movie>)
        movieListView.adapter = dataList

        movieListView.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val intent : Intent = NoteTakingActivity.newIntent(context)

                startActivity(intent)
                activity.overridePendingTransition(R.anim.left_to_right_enter, R.anim.left_to_right_exit)
            }

        }

        return view
    }

    override fun onResume() {
        super.onResume()
        movieListView.refreshDrawableState()
    }
    companion object {
        fun newInstance(): MoviesFragment = MoviesFragment()
    }


}
