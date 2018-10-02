package com.codemasa.codyabe.takeanote

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import android.text.InputType
import android.text.method.Touch
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.*
import kotlinx.android.synthetic.main.activity_note_edit.*
import kotlinx.android.synthetic.main.nav_header.view.*


class NoteEditActivity : AppCompatActivity() {
    internal lateinit var titleInputText : AppCompatEditText
    internal lateinit var directorInputText : AppCompatEditText
    internal lateinit var yearInputText : AppCompatEditText
    internal lateinit var categorySpinner : AppCompatSpinner
    internal lateinit var saveButton: Button
    internal lateinit var imageAddButton : ImageButton
    internal lateinit var imageClearButton : ImageButton
    internal var imageURL: String = ""

    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, NoteEditActivity::class.java)
            return intent
        }

        var TITLE_KEY = ""
        var DIRECTOR_KEY = ""
        var YEAR_KEY = ""
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
        categorySpinner = findViewById<AppCompatSpinner>(R.id.category_spinner)


        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val category = categorySpinner.selectedItem.toString()
                when(category) {
                    getString(R.string.movie_category) -> {
                        directorInputText.inputType = InputType.TYPE_CLASS_TEXT
                        directorInputText.setHint(getString(R.string.director_input))
                    }
                    getString(R.string.tv_category) -> {
                        directorInputText.inputType = InputType.TYPE_CLASS_NUMBER
                        directorInputText.setHint(getString(R.string.season_number))

                    }
                    getString(R.string.music_category) -> {
                        directorInputText.setHint(getString(R.string.artist))
                    }
                }
            }
        }


        imageAddButton = findViewById(R.id.image_add_button)
        imageAddButton.setOnClickListener {

            val pickPhoto = Intent(Intent.ACTION_OPEN_DOCUMENT,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickPhoto, 1)
        }

        imageClearButton = findViewById(R.id.clear_image_button)
        imageClearButton.setOnClickListener{view ->
            imageAddButton.setImageResource(R.drawable.ic_add)
            view.refreshDrawableState()
            imageURL = ""
        }

        val context = this

        val intent = intent
        if (intent.getStringExtra("type") == "edit") {
            titleInputText.setText(intent.getStringExtra("title"))
            directorInputText.setText(intent.getStringExtra("director"))
            yearInputText.setText(intent.getIntExtra("year", 0).toString())
            imageURL = intent.getStringExtra("imageURL")
            if (imageURL == "") {
                imageAddButton.setImageResource(R.drawable.ic_add)

            }
            else{
                imageAddButton.setImageURI(Uri.parse(imageURL))
            }

        }

        saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener { view ->

            val category = categorySpinner.selectedItem.toString()
            when (category) {
                getString(R.string.movie_category) -> {
                    if (titleInputText.text.isNotBlank() &&
                            directorInputText.text.isNotBlank() &&
                            yearInputText.text.isNotBlank()) {
                        val db = DatabaseHelper(context)
                        val movie = Movie(titleInputText.text.toString(), directorInputText.text.toString(), yearInputText.text.toString().toInt(), imageURL)
                        if (intent.getStringExtra("type") == "edit") {
                            val resultIntent = Intent()
                            resultIntent.putExtra("category", "movie")
                            resultIntent.putExtra("id", intent.getIntExtra("id",0))
                            resultIntent.putExtra("title", titleInputText.text.toString())
                            resultIntent.putExtra("director", directorInputText.text.toString())
                            resultIntent.putExtra("year", yearInputText.text.toString().toInt())
                            resultIntent.putExtra("imageURL", imageURL)
                            setResult(Activity.RESULT_OK, resultIntent)
                            finish()
                        } else {
                            db.insertData(movie)
                            val resultIntent = Intent()
                            resultIntent.putExtra("category", "movie")
                            setResult(Activity.RESULT_OK, resultIntent)
                            finish()
                        }

                    }
                }
                getString(R.string.tv_category) -> {
                    if (titleInputText.text.isNotBlank() &&
                            yearInputText.text.isNotBlank()) {
                        val db = DatabaseHelper(context)
                        val tvShow = TVShow(titleInputText.text.toString(), directorInputText.text.toString().toInt(), yearInputText.text.toString().toInt(), imageURL)
                        if (intent.getStringExtra("type") == "edit") {
                            val resultIntent = Intent()
                            resultIntent.putExtra("category", "tvShow")
                            resultIntent.putExtra("id", intent.getIntExtra("id",0))
                            resultIntent.putExtra("title", titleInputText.text.toString())
                            resultIntent.putExtra("season", directorInputText.text.toString().toInt())
                            resultIntent.putExtra("year", yearInputText.text.toString().toInt())
                            resultIntent.putExtra("imageURL", imageURL)
                            setResult(Activity.RESULT_OK, resultIntent)
                            finish()
                        } else {
                            val resultIntent = Intent()
                            resultIntent.putExtra("category", "tvShow")
                            setResult(Activity.RESULT_OK, resultIntent)
                            finish()
                            db.insertData(tvShow)
                        }

                    }
                }
                getString(R.string.music_category) -> {
                    if (titleInputText.text.isNotBlank() &&
                            directorInputText.text.isNotBlank() &&
                            yearInputText.text.isNotBlank()) {
                        val db = DatabaseHelper(context)
                        val album = Album(titleInputText.text.toString(), directorInputText.text.toString(), yearInputText.text.toString().toInt(),imageURL)
                        if (intent.getStringExtra("type") == "edit") {
                            db.updateAlbum(album.title, album.artist, album.releaseDate, album.imageURL)
                        } else {
                            db.insertData(album)
                        }

                        finish()
                    }
                }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
        when (requestCode) {
            0 -> if (resultCode == Activity.RESULT_OK) {
                val selectedImage = imageReturnedIntent.getData()
                imageAddButton.setImageURI(selectedImage)
                imageURL = selectedImage.toString()
            }
            1 -> if (resultCode == Activity.RESULT_OK) {
                val selectedImage = imageReturnedIntent.getData()
                imageAddButton.setImageURI(selectedImage)
                imageURL = selectedImage.toString()
            }
        }

    }

    fun hideKeyboard(view: View){
        val inputMethodManager : InputMethodManager = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromInputMethod(view.windowToken, 0)

    }
}