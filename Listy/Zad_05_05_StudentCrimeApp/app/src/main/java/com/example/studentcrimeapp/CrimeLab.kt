package com.example.studentcrimeapp

import java.util.*

object CrimeLab {
    private val crimes = mutableListOf<Crime>()
    init {
        for (i in 0 until 10) {
            val crime = Crime()
            crime.title = "Crime no $i"
            crime.isSolved = i % 4 == 0
            crimes += crime
        }
    }

    fun getCrimes(): List<Crime> {
        return crimes
    }

}