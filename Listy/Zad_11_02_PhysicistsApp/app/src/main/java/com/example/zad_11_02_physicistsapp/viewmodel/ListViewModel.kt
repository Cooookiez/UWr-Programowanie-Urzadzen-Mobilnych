package com.example.zad_11_02_physicistsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.zad_11_02_physicistsapp.model.Physicist

class ListViewModel(application: Application) : AndroidViewModel(application) {
    val physicists = MutableLiveData<List<Physicist>>()
    val physicistsLoadError = MutableLiveData<Boolean>()
    val physicistLoading = MutableLiveData<Boolean>()

    fun init() {
        val physicist1 = Physicist(
            0L,
            0L,
            "name",
            "1300-1400",
            "Połland",
            "Być czy nie być",
            "null"
        )
        val physicistList: ArrayList<Physicist> = arrayListOf(physicist1)
        physicists.value = physicistList
        physicistsLoadError.value = false
        physicistLoading.value = false
    }
}