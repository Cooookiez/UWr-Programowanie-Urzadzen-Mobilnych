package com.example.zad_11_02_physicistsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zad_11_02_physicistsapp.model.Physicist

class DetailViewModel : ViewModel() {
    val physicist = MutableLiveData<Physicist>()

    fun fetch() {
        val physicist1 = Physicist(
            0L,
            0L,
            "name",
            "1300-1400",
            "Połland",
            "Być czy nie być",
            "null"
        )
        physicist.value = physicist1
    }
}