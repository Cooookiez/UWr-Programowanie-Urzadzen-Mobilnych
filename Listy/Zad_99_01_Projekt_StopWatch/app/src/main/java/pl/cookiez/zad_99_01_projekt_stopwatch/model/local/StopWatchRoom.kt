package pl.cookiez.zad_99_01_projekt_stopwatch.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.cookiez.zad_99_01_projekt_stopwatch.model.StopWatch

@Database(entities = [StopWatch::class], version = 1, exportSchema = false)
abstract class StopWatchRoom : RoomDatabase() {
    abstract fun stopWatchDAO(): StopWatchDAO

    companion object {
        @Volatile private var instance: StopWatchRoom? = null
        private val LOCK = Any()

        private fun buildRoom(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            StopWatchRoom::class.java,
            "room__stopwatch"
        ).build()
    }
}