package com.codemasa.codyabe.takeanote

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem

class NoteTakingActivity : AppCompatActivity() {

    internal lateinit var recyclerView: RecyclerView
    internal lateinit var noteAdapter: NoteListAdapter


    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, NoteTakingActivity::class.java)
            return intent
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_note_taking)
        Log.e("Main", "Created Layout")

        val toolbar: Toolbar = findViewById(R.id.note_taking_toolbar)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
            setTitle("Note Editor")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                overridePendingTransition(R.anim.right_to_left_enter, R.anim.right_to_left_exit)
                true
            }
        }
        return false
    }



}