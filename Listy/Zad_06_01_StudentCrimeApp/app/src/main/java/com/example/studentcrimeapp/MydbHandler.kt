package com.example.studentcrimeapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.annotation.Nullable
import java.util.*
import kotlin.collections.ArrayList

class MydbHandler : SQLiteOpenHelper {

    companion object {
        private val DATABASE_VERSION: Int = 1
        private val DATABASE_NAME: String = "student_crimes.db"
        private val TABLE_NAME: String = "crimes"
        private val COLUMN_ID: String = "id"
        private val COLUMN_UUID: String = "uuid"
        private val COLUMN_TITLE: String = "title"
        private val COLUMN_DATE: String = "date"
        private val COLUMN_SOLVED: String = "solved"
    }

    constructor(@Nullable context: Context?)
            : super(context, DATABASE_NAME, null, DATABASE_VERSION)

    override fun onCreate(db: SQLiteDatabase?) {
        val crimeTable =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_UUID TEXT, " +
                "$COLUMN_TITLE TEXT, " +
                "$COLUMN_DATE INTEGER, " +
                "$COLUMN_SOLVED BOOLEAN" +
            ");"
        db!!.execSQL(crimeTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getCrimes(): Cursor {
        val query: String = "SELECT * FROM $TABLE_NAME"
        val db: SQLiteDatabase = this.readableDatabase
        return db.rawQuery(query, null)
    }

    fun addCrime(crime: Crime) {
        val contentValues: ContentValues = ContentValues()
        contentValues.put(COLUMN_UUID, crime.getId().toString())
        contentValues.put(COLUMN_TITLE, crime.getTitle())
        contentValues.put(COLUMN_DATE, crime.getDate().time)
        contentValues.put(COLUMN_SOLVED, crime.getSolved())

        val db: SQLiteDatabase = this.writableDatabase
        db.insert(TABLE_NAME, null, contentValues)
    }

    fun deleteCrime(uuid: UUID) {
        val query: String = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_UUID = \"$uuid\""
        val db: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            db.delete(
                TABLE_NAME,
                "$COLUMN_UUID = ?",
                listOf<String>(
                    uuid.toString()
                ).toTypedArray()
            )
        }
        cursor.close()
    }

    fun updateCrime(crime: Crime) {
        val query: String = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_UUID = \"${crime.getId()}\""
        val db: SQLiteDatabase = this.writableDatabase
        val cursor: Cursor = db.rawQuery(query, null)

        val contentValues: ContentValues = ContentValues()
        contentValues.put(COLUMN_UUID, crime.getId().toString())
        contentValues.put(COLUMN_TITLE, crime.getTitle())
        contentValues.put(COLUMN_DATE, crime.getDate().time)
        contentValues.put(COLUMN_SOLVED, crime.getSolved())

        if (cursor.moveToFirst()) {
            db.update(
                TABLE_NAME,
                contentValues,
                COLUMN_UUID,
                listOf<String>(
                    crime.getId().toString()
                ).toTypedArray()
            )
        }
        cursor.close()
    }

}