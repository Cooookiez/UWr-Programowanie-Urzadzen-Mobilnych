package com.example.zad_11_02_physicistsapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.zad_11_02_physicistsapp.model.Physicist
import com.example.zad_11_02_physicistsapp.model.local.PhysicistRoom
import com.example.zad_11_02_physicistsapp.model.remote.PhysicistsService
import com.example.zad_11_02_physicistsapp.util.SharedPreferencesHelper
import com.example.zad_11_02_physicistsapp.util.refreshTime
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListViewModel(application: Application)
    : AndroidViewModel(application), CoroutineScope {

    val physicists = MutableLiveData<List<Physicist>>()
    val physicistsLoadError = MutableLiveData<Boolean>()
    val physicistLoading = MutableLiveData<Boolean>()

    private var preferencesHelper = SharedPreferencesHelper(getApplication())

    private val physicistService = PhysicistsService()
    private val disposable = CompositeDisposable()

    private val job by lazy { Job() }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun refresh() {
        val updateTime: Long? = preferencesHelper.getUpdateTime()
        if (
            updateTime != null  &&
            updateTime != 0L    &&
            System.nanoTime() - updateTime < refreshTime
        )
            fetchLocal()
        else
            fetchRemote()
    }

    fun refreshFromRemote() {
        fetchRemote()
    }

    private fun fetchLocal() {
        physicistLoading.value = true
        launch {
            val physicists: List<Physicist> =
                PhysicistRoom(getApplication()).physicistDAO().getAllPhysicists()
            dataRetrieved(physicists)
            Toast.makeText(getApplication(), "LOCAL", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchRemote() {
        physicistLoading.value = true
        disposable.add(
            physicistService.getPhysicists()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Physicist>>() {
                    override fun onSuccess(physicistList: List<Physicist>?) {
                        insertToLocal(physicistList!!)
                        Toast.makeText(getApplication(), "REMOTE", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable?) {
                        physicistLoading.value = false
                        physicistsLoadError.value = true
                        e?.printStackTrace()
                    }

                })
        )
    }

    private fun dataRetrieved(physicistList: List<Physicist>) {
        physicists.value = physicistList
        physicistLoading.value = false
        physicistsLoadError.value = false
    }

    private fun insertToLocal(physicistList: List<Physicist>) {
        launch { // other thread
            val dao = PhysicistRoom(getApplication()).physicistDAO()
            dao.deleteAllPhysicists()
            val resultUuid = dao.insertAll(*physicistList.toTypedArray())
            for (i in resultUuid.indices) { // write uuid to array
                resultUuid[i].also { physicistList[i].uuid = it }
            }
            dataRetrieved(physicistList)
        }
        preferencesHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
        job.cancel()
    }
}