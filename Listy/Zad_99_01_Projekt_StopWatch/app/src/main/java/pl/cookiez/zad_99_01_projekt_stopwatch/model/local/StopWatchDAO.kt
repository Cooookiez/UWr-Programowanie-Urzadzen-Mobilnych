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
        "UPDATE `StopWatches` SET `title` = :title, `background_color` = :background_color, `background_url` = :background_url, `time_start` = :time_start, `time_saved_from_previous_counting` = :time_saved_from_previous_counting, `stop_watch_is_counting` = :stop_watch_is_counting WHERE `uuid` = :uuid"
    )
    suspend fun updateStopWatch(
        uuid: String,
        title: String,
        background_color: String,
        background_url: String,
        time_start: Long,
        time_saved_from_previous_counting: Long,
        stop_watch_is_counting: Boolean
    )

    @Query("SELECT * FROM `stopwatches`")
    suspend fun getAllStopWatches(): List<StopWatch>

    @Query("SELECT * FROM `stopwatches` WHERE `uuid` = :uuid")
    suspend fun getStopWatch(uuid: Long): StopWatch?

    @Query("DELETE FROM `stopwatches`")
    suspend fun deleteAllStopWatches()

}