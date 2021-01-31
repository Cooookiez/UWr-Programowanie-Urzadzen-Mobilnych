package pl.cookiez.zad_99_01_projekt_stopwatch.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import pl.cookiez.zad_99_01_projekt_stopwatch.model.StopWatch

@Dao
interface StopWatchDAO {

    @Insert
    suspend fun insertAll(vararg stopWatch: StopWatch): List<Long>

    @Query(
        "UPDATE `StopWatches` SET `position` = :position, `title` = :title, `background_color` = :background_color, `background_url` = :background_url, `time_start` = :time_start, `time_saved_from_previous_counting` = :time_saved_from_previous_counting, `time_str` = :time_str, `stop_watch_is_counting` = :stop_watch_is_counting WHERE `uuid` = :uuid"
    )
    suspend fun updateStopWatch(
        uuid: String,
        position: Int?,
        title: String?,
        background_color: String?,
        background_url: String?,
        time_start: Long?,
        time_saved_from_previous_counting: Long?,
        time_str: String?,
        stop_watch_is_counting: Boolean?
    )
    suspend fun updateStopWatchFromObject(stopWatch: StopWatch) {
        updateStopWatch(
            stopWatch.uuid.toString(),
            stopWatch.position,
            if (stopWatch.title == null) null else stopWatch.title,
            if (stopWatch.backgroundColor == null) null else stopWatch.backgroundColor,
            if (stopWatch.backgroundUrl == null) null else stopWatch.backgroundUrl,
            stopWatch.timeStart,
            stopWatch.timeSavedFromPreviousCounting,
            stopWatch.timeStr,
            stopWatch.stopWatchIsCounting
        )
    }
    suspend fun updateStopWatchesFromObjects(stopWatches: List<StopWatch>) {
        for (i in stopWatches.indices) {
            updateStopWatchFromObject(stopWatches[i])
        }
    }

    @Query("SELECT * FROM `stopwatches`")
    suspend fun getAllStopWatches(): List<StopWatch>

    @Query("SELECT * FROM `stopwatches` WHERE `uuid` = :uuid")
    suspend fun getStopWatch(uuid: Long): StopWatch?

    @Query("DELETE FROM `stopwatches`")
    suspend fun deleteAllStopWatches()

}