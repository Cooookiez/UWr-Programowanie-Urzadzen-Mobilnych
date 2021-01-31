package pl.cookiez.zad_99_01_projekt_stopwatch.util

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter


var notifyTime: Long = 30 * 60 * 1000 * 1000 * 1000L // m * s * ms * us * ns

fun nanoTime2strTimeHMS(timeNano: Long): String {
    // count s, m, h from time_nano
    var time: Long = timeNano / 1000_000_000
    Log.d("zaq1", "time = $time")
    val s: Long = time - (time / 60) * 60L
    time = (time - s) / 60
    val m: Long = time - (time / 60) * 60L
    time = (time - m) / 60
    val h: Long = time

    // convert count s, m, h from Long to Text
    val sStr: String = if (s >= 10) "$s" else "0$s"
    val mStr: String = if (m >= 10) "$m" else "0$m"
    val timeStr = if (h > 0) "$h:$mStr:$sStr" else "$mStr:$sStr"

    return timeStr
}