package com.codemasa.codyabe.takeanote

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity

class NoteEditActivity : AppCompatActivity() {
    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, NoteEditActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }
}