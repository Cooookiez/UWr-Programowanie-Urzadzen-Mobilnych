package com.example.zad_10_05_mvvm_localroom.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDAO {

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getSortedWords(): Flow<List<WordEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: WordEntity)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

}