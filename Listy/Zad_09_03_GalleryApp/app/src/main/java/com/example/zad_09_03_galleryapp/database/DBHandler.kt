package com.example.zad_09_03_galleryapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
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
        db?.execSQL(CREATE_GALLERY_TABLE)
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

    fun getAllItems(): ArrayList<SingleItemModel> {
        val itemList: ArrayList<SingleItemModel> = ArrayList() // pusta lista kotlin
        val selectQuery = "SELECT * FROM $TABLE_GALLERY"
        val db = this.readableDatabase

        try {
            val cursor: Cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val place = SingleItemModel(
                            cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                            cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                            cursor.getString(cursor.getColumnIndex(KEY_IMAGE))
                    )
                    itemList.add(place)
                } while (cursor.moveToNext())
                cursor.close()
            }
        } catch (e: SQLiteException) {
            e.printStackTrace()
            return ArrayList()
        }
        return itemList
    }

}
