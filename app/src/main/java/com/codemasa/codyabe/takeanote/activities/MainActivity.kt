package com.codemasa.codyabe.takeanote.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.codemasa.codyabe.takeanote.BuildConfig
import com.codemasa.codyabe.takeanote.model.DatabaseHelper
import com.codemasa.codyabe.takeanote.fragments.*
import com.codemasa.codyabe.takeanote.R

class MainActivity : AppCompatActivity() {

    internal lateinit var mDrawerLayout: DrawerLayout
    internal val TAG = MainActivity::class.java.simpleName
    internal lateinit var currentFragment : Fragment
    internal var currentFragmentID : Int = 0
    internal lateinit var drawerNotesFragment: DrawerNotesFragment
    internal lateinit var drawerProfileFragment: DrawerProfileFragment
    internal lateinit var drawerFavoritesFragment: DrawerFavoritesFragment
    internal lateinit var drawerBacklogFragment: DrawerBacklogFragment
    internal lateinit var drawerSettingsFragment: DrawerSettingsFragment
    internal lateinit var drawerSearchFragment: DrawerSearchFragment
    internal lateinit var searchBar : SearchView
    internal lateinit var infoButton : ImageButton
    companion object {
        var CURRENT_DRAWER_FRAGMENT_KEY = "CURRENT_DRAWER_FRAGMENT_KEY"
        var CURRENT_DRAWER_FRAGMENT_ID_KEY = "CURRENT_DRAWER_FRAGMENT_ID_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
            setTitle(R.string.take_a_note)
        }
        searchBar = findViewById(R.id.media_search)
        searchBar.visibility = View.GONE


        infoButton = findViewById(R.id.info_button)
        infoButton.setOnClickListener {
            showInfo()
        }


        //setting the layout for the drawer layout
        mDrawerLayout = findViewById(R.id.drawer_layout)

        drawerNotesFragment = DrawerNotesFragment.newInstance()
        drawerProfileFragment = DrawerProfileFragment.newInstance()
        drawerFavoritesFragment = DrawerFavoritesFragment.newInstance()
        drawerBacklogFragment = DrawerBacklogFragment.newInstance()
        drawerSettingsFragment = DrawerSettingsFragment.newInstance()
        drawerSearchFragment = DrawerSearchFragment.newInstance()




        if(savedInstanceState != null){
            currentFragment = supportFragmentManager.getFragment(savedInstanceState, CURRENT_DRAWER_FRAGMENT_KEY)
            openFragment(currentFragment)
        }
        else{
            currentFragment = drawerNotesFragment
            openFragment(drawerNotesFragment)
        }

        val navigationView: NavigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true

            // close drawer when item is tapped
            mDrawerLayout.closeDrawers()
            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here
            when (menuItem.itemId){
//                R.id.nav_profile -> {
//                    currentFragmentID = R.id.nav_profile
//                    currentFragment = drawerProfileFragment
//                    openFragment(drawerProfileFragment)
//                    actionbar?.setTitle(R.string.take_a_note)
//                    searchBar.visibility = View.GONE
//                    searchBar.isIconified = true
//                    return@setNavigationItemSelectedListener true
//                }
                R.id.nav_notes -> {
                    currentFragmentID = R.id.nav_notes

                    currentFragment = drawerNotesFragment
                    openFragment(drawerNotesFragment)
                    actionbar?.setTitle(R.string.take_a_note)
                    searchBar.visibility = View.GONE
                    searchBar.isIconified = true
                    return@setNavigationItemSelectedListener true
                }
                R.id.nav_favories -> {
                    currentFragmentID = R.id.nav_favories

                    currentFragment = drawerFavoritesFragment
                    openFragment(drawerFavoritesFragment)
                    actionbar?.setTitle(R.string.take_a_note)
                    searchBar.visibility = View.GONE
                    searchBar.isIconified = true

                    return@setNavigationItemSelectedListener true
                }
//                R.id.nav_backlog -> {
//                    currentFragmentID = R.id.nav_backlog
//
//                    currentFragment = drawerBacklogFragment
//                    openFragment(drawerBacklogFragment)
//                    actionbar?.setTitle(R.string.take_a_note)
//                    searchBar.visibility = View.GONE
//                    searchBar.isIconified = true
//
//                    return@setNavigationItemSelectedListener true
//                }
//                R.id.nav_search -> {
//                    currentFragmentID = R.id.nav_search
//                    currentFragment = drawerSearchFragment
//                    openFragment(drawerSearchFragment)
//                    actionbar?.title = ""
//                    searchBar.visibility = View.VISIBLE
//                    searchBar.isIconified = false
//
//                    return@setNavigationItemSelectedListener true
//                 }
//                R.id.nav_manage -> {
//                    currentFragmentID = R.id.nav_manage
//
//                    currentFragment = drawerSettingsFragment
//                    openFragment(drawerSettingsFragment)
//                    actionbar?.setTitle(R.string.take_a_note)
//                    searchBar.visibility = View.GONE
//                    searchBar.isIconified = true
//
//                    return@setNavigationItemSelectedListener true
//                }
            }


            false
        }

        mDrawerLayout.addDrawerListener(
                object : DrawerLayout.DrawerListener {
                    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                        // Respond when the drawer's position changes
                    }

                    override fun onDrawerOpened(drawerView: View) {
                        val drawerName : TextView = findViewById(R.id.drawer_name)
                        drawerName.text  = getString(R.string.take_a_note)
                        val drawerHeader : TextView = findViewById(R.id.drawer_header)
                        drawerHeader.text = getString(R.string.drawer_header_text)
                        val drawerHeaderSecondary : TextView = findViewById(R.id.drawer_header_secondary)
                        val db = DatabaseHelper(context)
                        val countMovies = db.readMovies().size
                        val countTVShows = db.readTVShows().size
                        drawerHeaderSecondary.text = getString(R.string.drawer_header_secondary_text).format(countMovies + countTVShows)
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
                searchBar.isIconified = true
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if(currentFragment == drawerNotesFragment ){
            if(drawerNotesFragment.currentFragment == drawerNotesFragment.homeFragment){
                super.onBackPressed()
            }
            else{
                drawerNotesFragment.currentFragment = drawerNotesFragment.homeFragment
                drawerNotesFragment.openFragment(drawerNotesFragment.homeFragment, 0)
            }
        }
        else{
            super.onBackPressed()
        }


    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        closeContextMenu()

        when(item.itemId) {
            1 -> {
                true
            }
        }

        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                2 -> {
                    currentFragment.onActivityResult(requestCode, resultCode, data)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)


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

    private fun showInfo() {
        val dialogTitle = getString(R.string.about_title, BuildConfig.VERSION_NAME)
        val dialogMessage = getString(R.string.about_message)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.create().show()
    }


}
