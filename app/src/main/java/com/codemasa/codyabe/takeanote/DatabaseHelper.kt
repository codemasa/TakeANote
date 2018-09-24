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

    fun readMovies() : MutableList<Movie>{
        var list : MutableList<Movie> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM "  + MOVIE_TABLE
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()) {
            do{
                var movie = Movie()
                movie.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                movie.title = result.getString(result.getColumnIndex(COL_TITLE))
                movie.director = result.getString(result.getColumnIndex(COL_DIRECTOR))
                movie.releaseDate = result.getString(result.getColumnIndex(COL_RELEASE_DATE)).toInt()
                list.add(movie)

            }while (result.moveToNext())
        }
        result.close()
        db.close()

        return list
    }

    fun deleteMovie(id : Int) {
        val db = this.writableDatabase

        db.delete(MOVIE_TABLE, COL_ID+"=?", arrayOf(id.toString()))
        db.close()
    }

    fun updateMovies(title: String?, director: String?, year: Int?){
        val db = this.writableDatabase
        val query = "SELECT * FROM "  + MOVIE_TABLE
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()) {
            do{
                val cv = ContentValues()
                cv.put(COL_TITLE, if(title.isNullOrBlank()) title  else result.getString(result.getColumnIndex(COL_TITLE)))
                cv.put(COL_DIRECTOR, if(director.isNullOrBlank()) director  else result.getString(result.getColumnIndex(COL_DIRECTOR)))
                cv.put(COL_RELEASE_DATE, if(year != null) year  else result.getInt(result.getColumnIndex(COL_RELEASE_DATE)))
                db.update(MOVIE_TABLE, cv, COL_ID+"=?", arrayOf(result.getString(result.getColumnIndex(COL_ID))))

            }while (result.moveToNext())
        }
        result.close()
        db.close()
    }

    fun findMovie(movie : Movie) : Boolean{
        val db = this.readableDatabase
        val query = "SELECT * FROM %s WHERE title = \"%s\" AND director = \"%s\"".format(MOVIE_TABLE, movie.title, movie.director)
        val result = db.rawQuery(query, null)
        if (result != null) {
            result.close()
            db.close()
            return true
        }
        db.close()
        return false
    }


}