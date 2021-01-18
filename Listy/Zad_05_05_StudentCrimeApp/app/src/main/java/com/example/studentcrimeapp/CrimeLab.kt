package com.example.studentcrimeapp

import android.content.Context
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class CrimeLab {

    companion object {
        private var crimeLab: CrimeLab? = null
        operator fun get(context: Context?): CrimeLab? {
            if (crimeLab == null)
                crimeLab = CrimeLab(context!!)
            return crimeLab
        }
    }
    private var crimes = ArrayList<Crime>(0)

    constructor(context: Context) {
        this.crimes = ArrayList(0)
        for (i in 0 until 100) {
            val crime: Crime = Crime()
            val bSolved: Boolean = i%2 == 0
            crime.setTitle("Crime no. $i")
            crime.setSolvedTo(bSolved)
            this.crimes.add(crime)
        }
    }

    fun addEmpty() { crimes.add(0, Crime()) }

    fun getCrimes(): List<Crime> { return this.crimes }

    fun getValueById(id: UUID) : Crime {
        for (crime in crimes) {
            if ( id == UUID.fromString(crime.getId().toString()) ) {
                return crime
            }
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

}