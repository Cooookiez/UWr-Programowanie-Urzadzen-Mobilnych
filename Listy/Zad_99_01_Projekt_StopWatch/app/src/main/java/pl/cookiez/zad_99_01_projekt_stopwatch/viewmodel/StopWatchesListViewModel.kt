package pl.cookiez.zad_99_01_projekt_stopwatch.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import pl.cookiez.zad_99_01_projekt_stopwatch.model.StopWatch
import pl.cookiez.zad_99_01_projekt_stopwatch.model.local.StopWatchDAO
import pl.cookiez.zad_99_01_projekt_stopwatch.model.local.StopWatchRoom
import pl.cookiez.zad_99_01_projekt_stopwatch.util.SharedPreferencesHelper
import pl.cookiez.zad_99_01_projekt_stopwatch.util.notifyTime
import java.lang.NumberFormatException
import kotlin.coroutines.CoroutineContext

class StopWatchesListViewModel(application: Application)
    : AndroidViewModel(application), CoroutineScope {

    val stopWatchesList = MutableLiveData<List<StopWatch>>()
    val stopWatchesLoadingError = MutableLiveData<Boolean>()
    val stopWatchesLoading = MutableLiveData<Boolean>()

    private var preferencesHelper = SharedPreferencesHelper(getApplication())
    private var stopWatchRoom = StopWatchRoom(getApplication()).stopWatchDAO()
    private var disposable = CompositeDisposable()

    private val job by lazy { Job() }

    init {
//        val stopWatch1 = StopWatch(
//            1,
//            "Untitled 1",
//            "",
//            "",
//            0L,
//            0L,
//            false
//        )
//        val stopWatch2 = StopWatch(
//            2,
//            "Untitled 2",
//            "",
//            "",
//            0L,
//            0L,
//            false
//        )
//        val stopWatchesLis0: ArrayList<StopWatch> = arrayListOf(
//            stopWatch1,
//            stopWatch2
//        )
//        insertToLocal(stopWatchesLis0)
//        this.stopWatchesList.value = stopWatchesLis0
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun checkStoredNotifyTime() {
        val storedNotifyTime = preferencesHelper.getStoredNotifyTime()
        try {
            val time: Int = storedNotifyTime?.toInt() ?: 30
            notifyTime = time.toLong() * 60 * 1000 * 1000 * 1000L
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
    }

    fun refresh() = fetchLocal()
    private fun fetchLocal() {
        stopWatchesLoading.value = true
        launch {
            val stopWatchesList: List<StopWatch> =
                stopWatchRoom.getAllStopWatches()
            dataRetrieved(stopWatchesList)
        }
    }

    private fun dataRetrieved(stopWatchesList: List<StopWatch>) {
        this.stopWatchesList.value = stopWatchesList
        stopWatchesLoadingError.value = false
        stopWatchesLoading.value = false
    }

    fun insertToLocal(stopWatchesList: List<StopWatch>) {
        launch {
            stopWatchRoom.deleteAllStopWatches()
            val resultUUID = stopWatchRoom.insertAll(*stopWatchesList.toTypedArray())
            for (i in resultUUID.indices) {
                resultUUID[i].also { stopWatchesList[i].uuid = it }
            }
            dataRetrieved(stopWatchesList)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
        job.cancel()
    }

}