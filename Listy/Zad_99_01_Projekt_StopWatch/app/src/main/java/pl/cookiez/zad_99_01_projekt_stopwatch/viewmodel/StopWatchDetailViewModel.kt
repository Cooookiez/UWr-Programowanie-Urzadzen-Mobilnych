package pl.cookiez.zad_99_01_projekt_stopwatch.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import pl.cookiez.zad_99_01_projekt_stopwatch.model.StopWatch
import pl.cookiez.zad_99_01_projekt_stopwatch.model.local.StopWatchRoom
import kotlin.coroutines.CoroutineContext

class StopWatchDetailViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    private var stopWatchRoom = StopWatchRoom(getApplication()).stopWatchDAO()
    val stopWatchesList = MutableLiveData<List<StopWatch>>()

    val stopWatch = MutableLiveData<StopWatch>()

    private val job by lazy { Job() }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun fetch(uuid: Long) {
        launch {
            val stopWatchElement = StopWatchRoom(getApplication())
                .stopWatchDAO().getStopWatch(uuid)
            stopWatch.value = stopWatchElement
        }
    }

    fun updateStopWatch(stopWatch: StopWatch) {
        launch {
            stopWatchRoom.updateStopWatch(
                stopWatch.uuid.toString(),
                stopWatch.position as Int,
                stopWatch.title.toString(),
                stopWatch.backgroundColor.toString(),
                stopWatch.backgroundUrl.toString(),
                stopWatch.timeStart as Long,
                stopWatch.timeSavedFromPreviousCounting as Long,
                stopWatch.stopWatchIsCounting as Boolean
            )
        }
    }

    fun deleteStopWatch(stopWatch: StopWatch) {
        val stopWatchesList = this.stopWatchesList.value as ArrayList<StopWatch>
        Log.d("zaq1", "s: ${stopWatchesList.size}")
        insertToLocal(stopWatchesList)
        stopWatchesList.removeAt(stopWatch.position!!)
        Log.d("zaq1", "s: ${stopWatchesList.size}")
        this.stopWatchesList.value = stopWatchesList as List<StopWatch>
        refresh()
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

    fun refresh() = fetchLocal()
    private fun fetchLocal() {
        launch {
            val stopWatchesList: List<StopWatch> =
                stopWatchRoom.getAllStopWatches()
            dataRetrieved(stopWatchesList)
        }
    }

    private fun dataRetrieved(stopWatchesList: List<StopWatch>) {
        this.stopWatchesList.value = stopWatchesList
    }

}