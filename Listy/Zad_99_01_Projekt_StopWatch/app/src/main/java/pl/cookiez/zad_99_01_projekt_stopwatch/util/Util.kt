package pl.cookiez.zad_99_01_projekt_stopwatch.util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.LinearLayout
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.databinding.BindingAdapter
import pl.cookiez.zad_99_01_projekt_stopwatch.model.StopWatch


var notifyTime: Long = 30 * 60 * 1000 * 1000 * 1000L // m * s * ms * us * ns
var autoCount: Boolean = true

fun nanoTime2strTimeHMS(timeNano: Long): String {
    // count s, m, h from time_nano
    var time: Long = timeNano / 1000_000_000
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

fun backgroundColor2hex(background: ColorDrawable): String {
    val r = background.color.red.toString(16)
    val g = background.color.green.toString(16)
    val b = background.color.blue.toString(16)
    return "#$r$g$b"
}

fun hex2background(hex: String): Drawable {
    val color = Color.parseColor(hex)
    return ColorDrawable(color)
}

//@BindingAdapter("image_bg")
//fun loadBg(view: LinearLayout, stopWatch: StopWatch?) {
//    if (stopWatch != null) {
//        view.background = hex2background("#ff00ff")
//    }
//}