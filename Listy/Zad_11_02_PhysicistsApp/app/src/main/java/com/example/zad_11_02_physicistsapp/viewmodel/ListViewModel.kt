package com.example.zad_11_02_physicistsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.zad_11_02_physicistsapp.model.Physicist
import com.example.zad_11_02_physicistsapp.model.PhysicistsService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class ListViewModel(application: Application) : AndroidViewModel(application) {
    val physicists = MutableLiveData<List<Physicist>>()
    val physicistsLoadError = MutableLiveData<Boolean>()
    val physicistLoading = MutableLiveData<Boolean>()

    private val physicistService = PhysicistsService()
    private val disposable = CompositeDisposable()

    fun refresh() {
        fatcheRemote()
    }

    private fun fatcheRemote() {
        physicistLoading.value = true
        disposable.add(
            physicistService.getPhysicists()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Physicist>>() {
                    override fun onSuccess(physicistList: List<Physicist>?) {
                        physicists.value = physicistList
                        physicistLoading.value = false
                        physicistsLoadError.value = false
                    }

                    override fun onError(e: Throwable?) {
                        physicistLoading.value = false
                        physicistsLoadError.value = true
                        e?.printStackTrace()
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}