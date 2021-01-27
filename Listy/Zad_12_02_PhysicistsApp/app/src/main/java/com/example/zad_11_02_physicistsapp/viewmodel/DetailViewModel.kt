package com.example.zad_11_02_physicistsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zad_11_02_physicistsapp.model.Physicist
import com.example.zad_11_02_physicistsapp.model.local.PhysicistRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    val physicist = MutableLiveData<Physicist>()

    private val job by lazy { Job() }
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun fetch(uuid: Long) {
        launch {
            val physicistElement =
                PhysicistRoom(getApplication())
                .physicistDAO()
                .getPhysicist(uuid)
            physicist.value = physicistElement
        }
    }
}