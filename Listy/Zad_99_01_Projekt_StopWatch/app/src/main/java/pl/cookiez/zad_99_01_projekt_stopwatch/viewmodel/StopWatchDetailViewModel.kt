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
import kotlin.coroutines.CoroutineContext

class StopWatchDetailViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    private var stopWatchRoom = StopWatchRoom(getApplication()).stopWatchDAO()

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
                stopWatch.title as String,
                stopWatch.backgroundColor as String,
                stopWatch.backgroundUrl as String,
                stopWatch.timeStart as Long,
                stopWatch.timeSavedFromPreviousCounting as Long,
                stopWatch.stopWatchIsCounting as Boolean
            )
        }
    }

}