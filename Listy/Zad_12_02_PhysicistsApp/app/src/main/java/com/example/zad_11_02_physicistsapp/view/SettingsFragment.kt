package com.example.zad_11_02_physicistsapp.view

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.zad_11_02_physicistsapp.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}