package pl.cookiez.zad_99_01_projekt_stopwatch.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pl.cookiez.zad_99_01_projekt_stopwatch.model.StopWatch

@Dao
interface StopWatchDAO {

    @Insert
    suspend fun insertAll(vararg stopWatch: StopWatch): List<Long>

    @Query("SELECT * FROM `stopwatches`")
    suspend fun getAllStopWatches(): List<StopWatch>

    @Query("SELECT * FROM `stopwatches` WHERE `uuid` = :uuid")
    suspend fun getStopWatch(uuid: Long): StopWatch?

    @Query("DELETE FROM `stopwatches`")
    suspend fun deleteAllStopWatches()

}