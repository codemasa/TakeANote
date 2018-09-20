package com.codemasa.codyabe.takeanote

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.renderscript.ScriptGroup
import android.support.constraint.ConstraintLayout
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.app.NavUtils
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.text.method.Touch
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.EditText



class NoteEditActivity : AppCompatActivity() {
    internal lateinit var textInput : TextInputLayout
    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, NoteEditActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)

        val toolbar: Toolbar = findViewById(R.id.note_toolbar)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
            setTitle("Note Editor")

        }
        textInput = findViewById(R.id.title_input)
        textInput.isActivated = true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun hideKeyboard(view: View){
        val inputMethodManager : InputMethodManager = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromInputMethod(view.windowToken, 0)

    }
}