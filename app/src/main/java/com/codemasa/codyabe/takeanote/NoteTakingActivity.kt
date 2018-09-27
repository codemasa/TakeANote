package com.codemasa.codyabe.takeanote

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.sql.Timestamp
import java.time.Instant
import java.time.format.DateTimeFormatter

class NoteTakingActivity : AppCompatActivity() {

    internal lateinit var noteListView: RecyclerView


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
            setTitle("Notes")
        }
        val category = intent.getStringExtra("category")
        val title = intent.getStringExtra("title")

        val db = DatabaseHelper(this)
        val notes = db.readNotes(category,title)
        val notesList = NoteListAdapter(this, notes)

        val intent = intent

        val sendButton: Button = findViewById(R.id.button_chatbox_send)
        sendButton.setOnClickListener{
            val noteTextEdit : TextView = findViewById(R.id.note_text_input)
            val noteText = noteTextEdit.text.toString()
            if (noteText.isNullOrBlank()){
                Toast.makeText(this, "Put a note to save", Toast.LENGTH_SHORT)
            }
            val note = Note(noteText, category, title, System.currentTimeMillis())
            notes.add(note)
            db.insertNote(note)
            notesList.notifyDataSetChanged()
            noteTextEdit.setText("")
            noteListView.smoothScrollToPosition(notes.size -1)

        }



        noteListView = findViewById(R.id.reyclerview_note_list)
        noteListView.adapter = notesList
        noteListView.layoutManager = LinearLayoutManager(this)
        if (notes.size > 0) {
            noteListView.smoothScrollToPosition(notes.size - 1)
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