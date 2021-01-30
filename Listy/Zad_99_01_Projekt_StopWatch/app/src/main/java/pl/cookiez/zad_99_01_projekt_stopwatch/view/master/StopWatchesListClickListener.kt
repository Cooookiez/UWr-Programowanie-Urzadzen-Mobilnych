package pl.cookiez.zad_99_01_projekt_stopwatch.view.master

import android.view.View

interface StopWatchesListClickListener {
    fun onMoreClicked(view: View)
    fun onPlayPauseClicked(view: View)
}