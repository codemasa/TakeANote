package com.codemasa.codyabe.takeanote

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
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
    internal lateinit var musicFragment: MusicFragment
    internal lateinit var tvFragment: TVFragment

    companion object {
        private val CURRENT_FRAGMENT_KEY = "CURRENT_FRAGMENT_KEY"
        private val CURRENT_NAV_ID = "CURRENT_NAV_ID"
        fun newInstance(): DrawerNotesFragment = DrawerNotesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_drawer_notes, container, false)
        mBottomNav = view.findViewById(R.id.entertainment_category)



        homeFragment = HomeFragment.newInstance()
        moviesFragment = MoviesFragment.newInstance()
        musicFragment = MusicFragment.newInstance()
        tvFragment = TVFragment.newInstance()

        if (savedInstanceState != null) {
            currentFragment = childFragmentManager.getFragment(savedInstanceState, CURRENT_FRAGMENT_KEY)
            openFragment(currentFragment)
        }
        else{
            currentFragment = homeFragment
            openFragment(currentFragment)
        }
        mBottomNav.setOnNavigationItemSelectedListener { item ->
            item.isChecked = true
            when (item.itemId) {
                R.id.home_nav_tab -> {
                    currentFragment = homeFragment
                    openFragment(homeFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.movie_nav_tab -> {
                    currentFragment = moviesFragment
                    openFragment(moviesFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tv_nav_tab -> {
                    currentFragment = tvFragment
                    openFragment(tvFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.music_nav_tab -> {
                    currentFragment = musicFragment
                    openFragment(musicFragment)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }


        return view
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        childFragmentManager.putFragment(outState, CURRENT_FRAGMENT_KEY,currentFragment)
        Log.d(TAG, "onSaveInstanceState: Saving Fragment ")
    }

    override fun onResume() {
        super.onResume()
        mBottomNav.menu.getItem(0).isChecked = true
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.bot_nav_container, fragment, fragment.tag)
        transaction.commit()
    }

}
