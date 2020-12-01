package com.example.studentcrimeapp

import androidx.fragment.app.Fragment

class CrimeListActivity : ListFragmentActivity() {
    override fun createFragment(): Fragment {
        return CrimeListFragment()
    }
}