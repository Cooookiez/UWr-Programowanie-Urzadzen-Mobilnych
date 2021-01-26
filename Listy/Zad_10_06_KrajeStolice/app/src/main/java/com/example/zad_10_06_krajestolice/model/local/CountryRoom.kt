package com.example.zad_10_06_krajestolice.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.zad_10_06_krajestolice.model.Country

@Database(entities = [Country::class], version = 1, exportSchema = false)
abstract class CountryRoom : RoomDatabase() {
    abstract fun countryDAO(): CountryDAO

    companion object {
        @Volatile private var instance: CountryRoom? = null
        private val LOCK = Any()

        private fun buildRoom(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CountryRoom::class.java,
            "countries_database_kotlin"
        ).build()

        // double check LOCK
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildRoom(context).also {
                instance = it
            }
        }
    }
}