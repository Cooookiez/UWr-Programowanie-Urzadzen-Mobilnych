package com.example.zad_10_05_mvvm_localroom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zad_10_05_mvvm_localroom.repository.WordRepository
import java.lang.IllegalArgumentException

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java))
            return WordViewModel(repository) as T
        throw IllegalArgumentException("Unknow ViewModel class")
    }

}