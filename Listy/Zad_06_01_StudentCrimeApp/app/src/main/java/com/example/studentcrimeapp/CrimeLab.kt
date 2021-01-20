package com.example.studentcrimeapp

import android.content.Context
import android.database.Cursor
import android.util.Log
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class CrimeLab {

    private var crimes = ArrayList<Crime>(0)
    private lateinit var dbHandler: MydbHandler
    var searchFilter: String = ""

    companion object {
        private var crimeLab: CrimeLab? = null
        operator fun get(context: Context?): CrimeLab? {
            if (crimeLab == null)
                crimeLab = CrimeLab(context!!)
            return crimeLab
        }
    }

    constructor(context: Context) {
        this.searchFilter = ""
        this.crimes = ArrayList(0)
        dbHandler = MydbHandler(context)
        reLoad()
    }

    fun reLoad() {
        this.crimes.clear()
        val cursor: Cursor = dbHandler.getCrimes()

        if (cursor.count != 0) {
            while (cursor.moveToNext()) {
                val title = cursor.getString(2)
                if (searchFilter == "" || title.toLowerCase().contains(searchFilter.toLowerCase())) {
                    val date: Date = Date()
                    date.time = cursor.getLong(3) // datetime

                    val crime: Crime = Crime()
                    crime.setId(UUID.fromString(cursor.getString(1))) // UUID
                    crime.setTitle(title)
                    crime.setDate(date)
                    crime.setSolvedTo(cursor.getInt(4) != 0) // is solved

                    this.crimes.add(crime)
                }
            }
        }
    }

    fun addEmpty() {
        reLoad()
        val crime = Crime()
        crime.setTitle(searchFilter)
        crimes.add(0, crime)
        dbHandler.addCrime(crime)
    }

    fun getCrimes(): List<Crime> {
        reLoad()
        return this.crimes
    }

    fun getCrimeById(uuid: UUID): Crime {
        for (crime in this.crimes) {
            if (uuid == crime.getId()) { return crime }
        }
        return Crime()
    }

    fun getIndexById(id: UUID) : Int {
        for (i in 0 until crimes.size) {
            if ( id == UUID.fromString(crimes[i].getId().toString()) ) {
                return i
            }
        }
        return -1
    }

    fun removeAtIndexOf(index: Int) {
        this.dbHandler.deleteCrime(crimes[index].getId())
        this.crimes.removeAt(index)
    }

    fun updateAtIndexOf(index: Int) {
        dbHandler.updateCrime(this.crimes[index])
    }

    fun setSearch(searchFilter: String) { this.searchFilter = searchFilter.toLowerCase() }
    fun getSearch(): String { return this.searchFilter }



}