package com.example.studentcrimeapp

import androidx.fragment.app.Fragment

class CrimeActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        return CrimeFragment(applicationContext)
    }

}