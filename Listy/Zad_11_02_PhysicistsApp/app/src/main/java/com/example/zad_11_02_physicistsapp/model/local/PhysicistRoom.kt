package com.example.zad_11_02_physicistsapp.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.zad_11_02_physicistsapp.model.Physicist

@Database(entities = [Physicist::class], version = 1, exportSchema = false)
abstract class PhysicistRoom : RoomDatabase() {
    abstract fun physicistDAO(): PhysicistDAO

    companion object {
        @Volatile private var instance: PhysicistRoom? = null
        private val LOCK = Any()

        private fun buiildRoom(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            PhysicistRoom::class.java,
            "physicist_database_kotlin"
        ).build()

        // double check LOCK
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buiildRoom(context).also {
                instance = it
            }
        }
    }
}