package com.codemasa.codyabe.takeanote

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import java.lang.reflect.Field

class MainActivity : AppCompatActivity() {

    internal lateinit var mDrawerLayout: DrawerLayout
    internal val TAG = MainActivity::class.java.simpleName
    internal lateinit var currentFragment : Fragment
    internal lateinit var drawerNotesFragment: DrawerNotesFragment


    companion object {
        var CURRENT_DRAWER_FRAGMENT_KEY = "CURRENT_DRAWER_FRAGMENT_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        //setting the layout for the drawer layout
        mDrawerLayout = findViewById(R.id.drawer_layout)

        drawerNotesFragment = DrawerNotesFragment.newInstance()



        
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true

            // close drawer when item is tapped
            mDrawerLayout.closeDrawers()
            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here
            when (menuItem.itemId){
                R.id.nav_profile -> {

                }
                R.id.nav_notes -> {
                    currentFragment = drawerNotesFragment
                    openFragment(drawerNotesFragment)
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_favories -> {

                }
                R.id.nav_backlog -> {

                }
                R.id.nav_manage -> {

                }
            }


            false
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
        supportFragmentManager.putFragment(outState, CURRENT_DRAWER_FRAGMENT_KEY,currentFragment)
        Log.d(TAG, "onSaveInstanceState: Saving Fragment ")
    }


    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame, fragment)
        transaction.commit()
    }


}
