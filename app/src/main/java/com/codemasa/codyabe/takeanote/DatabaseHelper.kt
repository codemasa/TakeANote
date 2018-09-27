package com.codemasa.codyabe.takeanote

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

// Database
val DATABASE_NAME = "TakeANoteDB"


// Tables
val MOVIE_TABLE = "movies"
val TV_TABLE = "tv"
val ALBUM_TABLE = "album"
val NOTE_TABLE = "note"

// Columns
val COL_ID = "id"
val COL_TITLE = "title"
val COL_DIRECTOR = "director"
val COL_ARTIST = "artist"
val COL_RELEASE_DATE = "date"
val COL_SEASON = "season"
val COL_NOTE = "note"
val COL_CATEGORY = "category"
val COL_FAVORITE = "favorite"
val COL_TIME = "time"

class DatabaseHelper(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {


    override fun onCreate(db: SQLiteDatabase?) {
        var createMovieTable = "CREATE TABLE "  +  MOVIE_TABLE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TITLE + " VARCHAR(256), " +
                COL_DIRECTOR + " VARCHAR(256), " +
                COL_RELEASE_DATE + " INTEGER, " +
                COL_FAVORITE + " BOOLEAN);"

        var createTVTable = "CREATE TABLE "  +  TV_TABLE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TITLE + " VARCHAR(256), " +
                COL_SEASON + " INTEGER, " +
                COL_RELEASE_DATE + " INTEGER, " +
                COL_FAVORITE + " BOOLEAN);"


        var createAlbumTable = "CREATE TABLE "  +  ALBUM_TABLE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TITLE + " VARCHAR(256), " +
                COL_ARTIST + " VARCHAR(256), " +
                COL_RELEASE_DATE + " INTEGER, " +
                COL_FAVORITE + " BOOLEAN);"

        var createNoteTable = "CREATE TABLE "  +  NOTE_TABLE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NOTE + " VARCHAR(256), " +
                COL_CATEGORY + " VARCHAR(256), " +
                COL_TITLE + " VARCHAR(256), " +
                COL_TIME + " LONG);"
        
        db?.execSQL(createMovieTable)
        db?.execSQL(createTVTable)
        db?.execSQL(createAlbumTable)
        db?.execSQL(createNoteTable)
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
        cv.put(COL_FAVORITE, false)
        var result = db.insert(MOVIE_TABLE, null, cv)
        if (result == -1.toLong()) {
            Toast.makeText(context,"Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context,"Saved", Toast.LENGTH_SHORT).show()

        }

    }
    fun insertData(tvShow: TVShow){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_TITLE, tvShow.title)
        cv.put(COL_SEASON, tvShow.season)
        cv.put(COL_RELEASE_DATE, tvShow.releaseDate)
        cv.put(COL_FAVORITE, false)
        var result = db.insert(TV_TABLE, null, cv)
        if (result == -1.toLong()) {
            Toast.makeText(context,"Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context,"Saved", Toast.LENGTH_SHORT).show()

        }

    }

    fun insertData(album: Album){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_TITLE, album.title)
        cv.put(COL_DIRECTOR, album.artist)
        cv.put(COL_RELEASE_DATE, album.releaseDate)
        cv.put(COL_FAVORITE, false)
        var result = db.insert(ALBUM_TABLE, null, cv)
        if (result == -1.toLong()) {
            Toast.makeText(context,"Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context,"Saved", Toast.LENGTH_SHORT).show()

        }

    }

    fun insertNote(note:Note){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_NOTE, note.noteBody)
        cv.put(COL_CATEGORY, note.category)
        cv.put(COL_TITLE, note.title)
        cv.put(COL_TIME, note.createdAt)
        val result = db.insert(NOTE_TABLE, null, cv)
        if (result == -1.toLong()) {
            Toast.makeText(context,"Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context,"Saved", Toast.LENGTH_SHORT).show()

        }
    }

    fun readMovies() : MutableList<Movie>{
        val list : MutableList<Movie> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM "  + MOVIE_TABLE
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()) {
            do{
                val movie = Movie()
                movie.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                movie.title = result.getString(result.getColumnIndex(COL_TITLE))
                movie.director = result.getString(result.getColumnIndex(COL_DIRECTOR))
                movie.releaseDate = result.getString(result.getColumnIndex(COL_RELEASE_DATE)).toInt()
                movie.favorite = result.getInt(result.getColumnIndex(COL_FAVORITE)) > 0

                list.add(movie)

            }while (result.moveToNext())
        }
        result.close()
        db.close()

        return list
    }

    fun readTVShows() : MutableList<TVShow>{
        val list : MutableList<TVShow> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM "  + TV_TABLE
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()) {
            do{
                var tvShow = TVShow()
                tvShow.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                tvShow.title = result.getString(result.getColumnIndex(COL_TITLE))
                tvShow.season = result.getString(result.getColumnIndex(COL_SEASON)).toInt()
                tvShow.releaseDate = result.getString(result.getColumnIndex(COL_RELEASE_DATE)).toInt()
                tvShow.favorite = result.getInt(result.getColumnIndex(COL_FAVORITE)) > 0
                list.add(tvShow)

            }while (result.moveToNext())
        }
        result.close()
        db.close()

        return list
    }

    fun readAlbums() : MutableList<Album>{
        val list : MutableList<Album> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM "  + ALBUM_TABLE
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()) {
            do{
                val album = Album()
                album.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                album.title = result.getString(result.getColumnIndex(COL_TITLE))
                album.artist = result.getString(result.getColumnIndex(COL_ARTIST))
                album.releaseDate = result.getString(result.getColumnIndex(COL_RELEASE_DATE)).toInt()
                album.favorite = result.getInt(result.getColumnIndex(COL_FAVORITE)) > 0

                list.add(album)

            }while (result.moveToNext())
        }
        result.close()
        db.close()

        return list
    }


    fun readNotes(category: String, title: String) : MutableList<Note>{
        val list : MutableList<Note> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM " + NOTE_TABLE + " WHERE category =?" + " AND title=?"
        val result = db.rawQuery(query, arrayOf(category,title))
        if(result.moveToFirst()) {
            do{
                val note = Note()
                note.noteBody = result.getString(result.getColumnIndex(COL_NOTE))
                note.category = result.getString(result.getColumnIndex(COL_CATEGORY))
                note.title = result.getString(result.getColumnIndex(COL_TITLE))
                note.createdAt = result.getLong(result.getColumnIndex(COL_TIME))
                list.add(note)

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

    fun deleteTVShow(id : Int) {
        val db = this.writableDatabase

        db.delete(TV_TABLE, COL_ID+"=?", arrayOf(id.toString()))
        db.close()
    }

    fun deleteAlbum(id : Int) {
        val db = this.writableDatabase

        db.delete(ALBUM_TABLE, COL_ID+"=?", arrayOf(id.toString()))
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

    fun updateTVShow(title: String?, season: Int?, year: Int?){
        val db = this.writableDatabase
        val query = "SELECT * FROM "  + TV_TABLE
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()) {
            do{
                val cv = ContentValues()
                cv.put(COL_TITLE, if(title.isNullOrBlank()) title  else result.getString(result.getColumnIndex(COL_TITLE)))
                cv.put(COL_SEASON, if(season != null) season  else result.getInt(result.getColumnIndex(COL_SEASON)))
                cv.put(COL_RELEASE_DATE, if(year != null) year  else result.getInt(result.getColumnIndex(COL_RELEASE_DATE)))
                db.update(TV_TABLE, cv, COL_ID+"=?", arrayOf(result.getString(result.getColumnIndex(COL_ID))))

            }while (result.moveToNext())
        }
        result.close()
        db.close()
    }

    fun updateAlbum(title: String?, artist: String?, year: Int?){
        val db = this.writableDatabase
        val query = "SELECT * FROM "  + ALBUM_TABLE
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()) {
            do{
                val cv = ContentValues()
                cv.put(COL_TITLE, if(title.isNullOrBlank()) title  else result.getString(result.getColumnIndex(COL_TITLE)))
                cv.put(COL_ARTIST, if(artist.isNullOrBlank()) artist  else result.getString(result.getColumnIndex(COL_DIRECTOR)))
                cv.put(COL_RELEASE_DATE, if(year != null) year  else result.getInt(result.getColumnIndex(COL_RELEASE_DATE)))
                db.update(ALBUM_TABLE, cv, COL_ID+"=?", arrayOf(result.getString(result.getColumnIndex(COL_ID))))

            }while (result.moveToNext())
        }
        result.close()
        db.close()
    }

    fun markAsFavorite(movie : Movie){
        val db = this.writableDatabase
        val query = "SELECT * FROM " + MOVIE_TABLE
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()) {
            do{
                if (movie.favorite == false) {
                    val cv = ContentValues()
                    cv.put(COL_FAVORITE, true)
                    db.update(MOVIE_TABLE, cv, COL_ID+"=?", arrayOf(movie.id.toString()))
                }
                else {
                    val cv = ContentValues()
                    cv.put(COL_FAVORITE, false)
                    db.update(MOVIE_TABLE, cv, COL_ID+"=?", arrayOf(movie.id.toString()))
                }
            }while (result.moveToNext())
        }
    }

    fun markAsFavorite(tvShow : TVShow){
        val db = this.writableDatabase
        val query = "SELECT * FROM " + TV_TABLE
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()) {
            do{
                if (tvShow.favorite == false) {
                    val cv = ContentValues()
                    cv.put(COL_FAVORITE, true)
                    db.update(TV_TABLE, cv, COL_ID+"=?", arrayOf(tvShow.id.toString()))
                }
                else {
                    val cv = ContentValues()
                    cv.put(COL_FAVORITE, false)
                    db.update(TV_TABLE, cv, COL_ID+"=?", arrayOf(tvShow.id.toString()))
                }
            }while (result.moveToNext())
        }
    }

    fun markAsFavorite(album: Album){
        val db = this.writableDatabase
        val query = "SELECT * FROM " + ALBUM_TABLE
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()) {
            do{
                if (album.favorite == false) {
                    val cv = ContentValues()
                    cv.put(COL_FAVORITE, true)
                    db.update(ALBUM_TABLE, cv, COL_ID+"=?", arrayOf(album.id.toString()))
                }
                else {
                    val cv = ContentValues()
                    cv.put(COL_FAVORITE, false)
                    db.update(ALBUM_TABLE, cv, COL_ID+"=?", arrayOf(album.id.toString()))
                }
            }while (result.moveToNext())
        }
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

    fun findTVShow(tvShow : TVShow) : Boolean{
        val db = this.readableDatabase
        val query = "SELECT * FROM %s WHERE title = \"%s\"".format(TV_TABLE, tvShow.title)
        val result = db.rawQuery(query, null)
        if (result != null) {
            result.close()
            db.close()
            return true
        }
        db.close()
        return false
    }

    fun findAlbum(album: Album) : Boolean{
        val db = this.readableDatabase
        val query = "SELECT * FROM %s WHERE title = \"%s\" AND artist = \"%s\"".format(ALBUM_TABLE, album.title, album.artist)
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