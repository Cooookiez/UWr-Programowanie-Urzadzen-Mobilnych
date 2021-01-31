package pl.cookiez.zad_99_01_projekt_stopwatch.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "StopWatches")
data class StopWatch(
    @PrimaryKey(autoGenerate = true)
    var uuid: Long?,
    var position: Int?,
    var title: String?,
    @ColumnInfo(name = "background_color")
    var backgroundColor: String?,
    @ColumnInfo(name = "background_url")
    var backgroundUrl: String?,
    @ColumnInfo(name = "time_start")
    var timeStart: Long?,
    @ColumnInfo(name = "time_saved_from_previous_counting")
    var timeSavedFromPreviousCounting: Long?,
    @ColumnInfo(name = "time_str")
    var timeStr: String?,
    @ColumnInfo(name = "stop_watch_is_counting")
    var stopWatchIsCounting: Boolean?
)