package pl.cookiez.zad_99_01_projekt_stopwatch.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPreferencesHelper {

    companion object {

        private var sharedPreferences: SharedPreferences? = null
        @Volatile private var instance: SharedPreferencesHelper? = null
        private val LOCK = Any()

        private fun buildHelper(context: Context): SharedPreferencesHelper {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferencesHelper()
        }

        operator fun invoke(context: Context): SharedPreferencesHelper =
            instance ?: synchronized(LOCK) {
                instance ?: buildHelper(context).also {
                    instance = it
                }
            }

    }

    fun getAutoPlay() = sharedPreferences?.getBoolean("auto_play", true)

}