package com.codemasa.codyabe.takeanote

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


val DATABASE_NAME = "TakeANoteDB"
val MOVIE_TABLE = "movies"
val COL_ID = "id"
val COL_TITLE = "title"
val COL_DIRECTOR = "director"
val COL_RELEASE_DATE = "date"

class DatabaseHelper(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {


    override fun onCreate(db: SQLiteDatabase?) {
        var createTable = "CREATE TABLE "  +  MOVIE_TABLE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TITLE + " VARCHAR(256), " +
                COL_DIRECTOR + " VARCHAR(256), " +
                COL_RELEASE_DATE + " INTEGER);"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertData(movie: Movie){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_TITLE, movie.title)
        cv.put(COL_DIRECTOR, movie.director)
        cv.put(COL_RELEASE_DATE, movie.releaseDate)
        var result = db.insert(MOVIE_TABLE, null, cv)
        if (result == -1.toLong()) {
            Toast.makeText(context,"Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context,"Saved", Toast.LENGTH_SHORT).show()

        }

    }

}