package com.example.zad_11_02_physicistsapp.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPreferencesHelper {

    companion object {
        private const val PREF_TIME = "pref_time"
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

    fun saveUpdateTime(time: Long) {
        sharedPreferences?.edit(commit = true) {
            putLong(PREF_TIME, time)
        }
    }

    fun getUpdateTime() = sharedPreferences?.getLong(PREF_TIME, 0)

    fun getStoredRefreshTime() = sharedPreferences?.getString("wait_for_sync_duration", "")

}