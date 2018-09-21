package com.codemasa.codyabe.takeanote

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.renderscript.ScriptGroup
import android.support.constraint.ConstraintLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.AppCompatEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.app.NavUtils
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatSpinner
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.text.method.Touch
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_note_edit.*


class NoteEditActivity : AppCompatActivity() {
    internal lateinit var titleInputText : AppCompatEditText
    internal lateinit var directorInputText : AppCompatEditText
    internal lateinit var yearInputText : AppCompatEditText
    internal lateinit var categorySpinner : AppCompatSpinner
    internal lateinit var saveButton: Button
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
        titleInputText = findViewById(R.id.title_input_text)
        directorInputText = findViewById(R.id.director_input_text)
        yearInputText = findViewById(R.id.date_input_text)
        categorySpinner = findViewById(R.id.category_spinner)




        val context = this
        saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener{ view ->
            if(titleInputText.text.isNotBlank()&&
                     directorInputText.text.isNotBlank() &&
                     yearInputText.text.isNotBlank()){
                var category = categorySpinner.selectedItem.toString()
                if (yearInputText.text.toString().toIntOrNull() != null){
                    when (category) {
                        getString(R.string.movie_category) -> {
                            var movie = Movie(titleInputText.text.toString(), directorInputText.text.toString(), yearInputText.text.toString().toInt())
                            var db = DatabaseHelper(context)
                            db.insertData(movie)
                            finish()
                        }
                        getString(R.string.tv_category) -> {

                        }
                        getString(R.string.music_category) -> {

                        }
                    }


                }

            }
            else{
                 Toast.makeText(context, getString(R.string.fill_message), Toast.LENGTH_LONG)
            }
        }

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