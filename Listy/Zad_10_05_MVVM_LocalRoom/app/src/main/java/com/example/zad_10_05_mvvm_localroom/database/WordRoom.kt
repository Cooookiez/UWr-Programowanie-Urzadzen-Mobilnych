package com.example.zad_10_05_mvvm_localroom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.zad_10_05_mvvm_localroom.model.WordDAO
import com.example.zad_10_05_mvvm_localroom.model.WordEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [WordEntity::class], version = 1, exportSchema = false)
abstract class WordRoom : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: WordRoom? = null

        fun getDatabase(context: Context, scope: CoroutineScope): WordRoom {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoom::class.java,
                    "word_database_kotlin"
                )
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun wordDAO(): WordDAO

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {database ->
                scope.launch {
                    populateDatabase(database.wordDAO())
                }
            }
        }

        suspend fun populateDatabase(wordDAO: WordDAO) {
            wordDAO.deleteAll()

            wordDAO.insert(WordEntity("Hello"))
            wordDAO.insert(WordEntity("PUM"))
        }
    }

}