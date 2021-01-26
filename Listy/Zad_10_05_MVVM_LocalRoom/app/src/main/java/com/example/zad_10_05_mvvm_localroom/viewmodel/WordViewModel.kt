package com.example.zad_10_05_mvvm_localroom.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.zad_10_05_mvvm_localroom.model.WordEntity
import com.example.zad_10_05_mvvm_localroom.repository.WordRepository
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository) : ViewModel() {

    val allWords: LiveData<List<WordEntity>> = repository.allWords.asLiveData()

    fun insert(word: WordEntity) = viewModelScope.launch {
        repository.insert(word)
    }

}