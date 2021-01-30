package pl.cookiez.zad_99_01_projekt_stopwatch.view

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import pl.cookiez.zad_99_01_projekt_stopwatch.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}