package com.example.zad_09_03_galleryapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.zad_09_03_galleryapp.models.SingleItemModel

class DBHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "galleryJavaKotlin.db"

        private const val TABLE_GALLERY = "GalleryTable"
        private const val KEY_ID = "_id"
        private const val KEY_TITLE = "title"
        private const val KEY_IMAGE = "image"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_GALLERY_TABLE = (
            "CREATE TABLE $TABLE_GALLERY(" +
            "$KEY_ID INTEGER PRIMARY KEY, " +
            "$KEY_TITLE TEXT, " +
            "$KEY_IMAGE TEXT" +
            ")"
        )
        db!!.execSQL(CREATE_GALLERY_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_GALLERY")
        onCreate(db)
    }

    fun addToGallery(singleItem: SingleItemModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, singleItem.title)
        contentValues.put(KEY_IMAGE, singleItem.image)

        val result = db.insert(TABLE_GALLERY, null, contentValues)
        db.close()
        return result
    }

}
