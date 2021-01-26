package com.example.zad_10_05_mvvm_localroom.repository

import androidx.annotation.WorkerThread
import com.example.zad_10_05_mvvm_localroom.model.WordDAO
import com.example.zad_10_05_mvvm_localroom.model.WordEntity
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDAO: WordDAO) {

    val allWords: Flow<List<WordEntity>> = wordDAO.getSortedWords()

    @WorkerThread
    suspend fun insert(word: WordEntity) {
        wordDAO.insert(word)
    }

}