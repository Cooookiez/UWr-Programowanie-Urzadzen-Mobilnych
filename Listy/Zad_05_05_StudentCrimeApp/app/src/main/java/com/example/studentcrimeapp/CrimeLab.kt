package com.example.studentcrimeapp

object CrimeLab {
    private val crimes = mutableListOf<Crime>()
    init {
        for (i in 0 until 100) {
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