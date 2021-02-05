package pl.cookiez.zad_99_01_projekt_stopwatch.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import pl.cookiez.zad_99_01_projekt_stopwatch.model.StopWatch
import pl.cookiez.zad_99_01_projekt_stopwatch.model.local.StopWatchRoom
import pl.cookiez.zad_99_01_projekt_stopwatch.util.SharedPreferencesHelper
import pl.cookiez.zad_99_01_projekt_stopwatch.util.autoCount
import java.lang.NumberFormatException
import kotlin.coroutines.CoroutineContext

class StopWatchesListViewModel(application: Application)
    : AndroidViewModel(application), CoroutineScope {

    val stopWatchesList = MutableLiveData<List<StopWatch>>()

    private var preferencesHelper = SharedPreferencesHelper(getApplication())
    private var stopWatchRoom = StopWatchRoom(getApplication()).stopWatchDAO()

    private val job by lazy { Job() }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun checkAutoCount() {
        val storedAutoCount = preferencesHelper.getAutoPlay()
        try {
            autoCount = storedAutoCount ?: true
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
    }

    fun refresh() = fetchLocal()
    private fun fetchLocal() {
        launch {
            val stopWatchesList: List<StopWatch> =
                stopWatchRoom.getAllStopWatches()
            for (i in stopWatchesList.indices) {
                stopWatchesList[i].position = i
            }
            stopWatchRoom.updateStopWatchesFromObjects(stopWatchesList)
            dataRetrieved(stopWatchesList)
        }
    }

    private fun dataRetrieved(stopWatchesList: List<StopWatch>) {
        this.stopWatchesList.value = stopWatchesList

    }

    fun updateStopWatch(stopWatch: StopWatch) {
        launch {
            stopWatchRoom.updateStopWatchFromObject(stopWatch)
        }
    }

    fun insertToLocal(stopWatchesList: List<StopWatch>) {
        launch {
            stopWatchRoom.deleteAllStopWatches()
            val resultUUID = stopWatchRoom.insertAll(*stopWatchesList.toTypedArray())
            for (i in resultUUID.indices) {
                resultUUID[i].also {
                    stopWatchesList[i].uuid = it
                    stopWatchesList[i].position = i
                }
            }
            dataRetrieved(stopWatchesList)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}