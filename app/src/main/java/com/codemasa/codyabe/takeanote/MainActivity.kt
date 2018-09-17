package com.codemasa.codyabe.takeanote

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View

class MainActivity : AppCompatActivity() {

    internal lateinit var mDrawerLayout: DrawerLayout
    internal lateinit var mBottomNav: BottomNavigationView
    internal val TAG = MainActivity::class.java.simpleName

    companion object {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentFragment = HomeFragment.newInstance()
        openFragment(currentFragment)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }


        mDrawerLayout = findViewById(R.id.drawer_layout)
        mBottomNav = findViewById(R.id.entertainment_category)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            mDrawerLayout.closeDrawers()

            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here

            true
        }
        mDrawerLayout.addDrawerListener(
                object : DrawerLayout.DrawerListener {
                    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                        // Respond when the drawer's position changes
                    }

                    override fun onDrawerOpened(drawerView: View) {
                        // Respond when the drawer is opened
                    }

                    override fun onDrawerClosed(drawerView: View) {
                        // Respond when the drawer is closed
                    }

                    override fun onDrawerStateChanged(newState: Int) {
                        // Respond when the drawer motion state changes
                    }
                }
        )
        mBottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_nav_tab -> {
                    val homeFragment = HomeFragment.newInstance()
                    openFragment(homeFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.movie_nav_tab -> {
                    val moviesFragment = MoviesFragment.newInstance()
                    openFragment(moviesFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tv_nav_tab -> {
                    val tvFragment = TVFragment.newInstance()
                    openFragment(tvFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.music_nav_tab -> {
                    val musicFragment = MusicFragment.newInstance()
                    openFragment(musicFragment)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                mDrawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState: Saving Fragment ")
    }
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.bot_nav_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
