package com.example.zad_10_06_krajestolice.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.zad_10_06_krajestolice.model.Country
import com.example.zad_10_06_krajestolice.model.local.CountryRoom
import com.example.zad_10_06_krajestolice.model.remote.CountryService
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

    val country = MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    private val countryService = CountryService()
    private val disposable = CompositeDisposable()

    private val job by lazy { Job() }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun refresh() {
        fetchLocal()
    }

    fun refreshFromRemote() {
        fetchRemote()
    }

    private fun fetchLocal() {
        countryLoading.value = true
        launch {
            val country: List<Country> =
                CountryRoom(getApplication()).countryDAO().getAllCountries()
            dataRetrieved(country)
            Toast.makeText(getApplication(), "LOCAL", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchRemote() {
        countryLoading.value = true
        disposable.add(
            countryService.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(countriesList: List<Country>?) {
                        insertToLocal(countriesList!!)
                        Toast.makeText(getApplication(), "REMOTE", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable?) {
                        countryLoading.value = false
                        countryLoadError.value = true
                        e?.printStackTrace()
                    }
                })
        )
    }

    private fun dataRetrieved(country: List<Country>) {
        this.country.value = country
        countryLoadError.value = false
        countryLoading.value = false
    }

    private fun insertToLocal(countriesList: List<Country>) {
        launch {
            val dao = CountryRoom(getApplication()).countryDAO()
            dao.deleteAllCountries()
            val resultUUID = dao.insertAll(*countriesList.toTypedArray())
            for (i in resultUUID.indices) {
                resultUUID[i].also { countriesList[i].uuid = it }
            }
            dataRetrieved(countriesList)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
        job.cancel()
    }
}