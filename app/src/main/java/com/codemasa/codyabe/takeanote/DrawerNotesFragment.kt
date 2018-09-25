package com.codemasa.codyabe.takeanote

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class DrawerNotesFragment : Fragment() {
    internal lateinit var mBottomNav: BottomNavigationView
    internal val TAG = DrawerNotesFragment::class.java.simpleName
    internal lateinit var currentFragment: Fragment
    internal lateinit var homeFragment: HomeFragment
    internal lateinit var moviesFragment: MoviesFragment
    internal lateinit var albumFragment: AlbumFragment
    internal lateinit var tvShowFragment: TVShowFragment



    companion object {
        private val CURRENT_FRAGMENT_KEY = "CURRENT_FRAGMENT_KEY"
        private val CURRENT_NAV_ID = "CURRENT_NAV_ID"
        fun newInstance(): DrawerNotesFragment = DrawerNotesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_drawer_notes, container, false)
        mBottomNav = view.findViewById(R.id.entertainment_category)
        homeFragment = HomeFragment.newInstance()
        moviesFragment = MoviesFragment.newInstance()
        albumFragment = AlbumFragment.newInstance()
        tvShowFragment = TVShowFragment.newInstance()


        if (savedInstanceState != null) {
            currentFragment = childFragmentManager.getFragment(savedInstanceState, CURRENT_FRAGMENT_KEY)
            openFragment(currentFragment, null)
        }
        else{
            currentFragment = homeFragment
            openFragment(homeFragment, null)
        }

        mBottomNav.setOnNavigationItemSelectedListener { item ->
            item.isChecked = true
            when (item.itemId) {
                R.id.home_nav_tab -> {
                    currentFragment = homeFragment
                    openFragment(homeFragment,null)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.movie_nav_tab -> {
                    currentFragment = moviesFragment
                    openFragment(moviesFragment,null)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tv_nav_tab -> {
                    currentFragment = tvShowFragment
                    openFragment(tvShowFragment,null)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.music_nav_tab -> {
                    currentFragment = albumFragment
                    openFragment(albumFragment,null)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
        var floatingActionButton : FloatingActionButton = view.findViewById(R.id.add_note_button)
        floatingActionButton.setOnClickListener {_->
            val intent : Intent = NoteEditActivity.newIntent(this.context)
            intent.putExtra("type", "new")
            startActivity(intent)
        }


        return view
    }
    override fun onSaveInstanceState(outState: Bundle) {
        childFragmentManager.putFragment(outState, CURRENT_FRAGMENT_KEY,currentFragment)
        Log.d(TAG, "onSaveInstanceState: Saving Fragment ")
        super.onSaveInstanceState(outState)
    }


    override fun onResume() {
        super.onResume()
    }

    public fun openFragment(fragment: Fragment, itemIndex : Int?) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.notes_layout, fragment, fragment.tag)
        transaction.commit()
        if(itemIndex != null){
            mBottomNav.menu.getItem(itemIndex).isChecked = true
        }
    }


}
