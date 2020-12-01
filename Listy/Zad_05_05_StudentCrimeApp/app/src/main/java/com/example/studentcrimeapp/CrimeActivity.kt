package com.example.studentcrimeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import java.util.*

class CrimeActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val position: Int = intent.getIntExtra("position", 0)
        val crimeList: List<Crime> = CrimeLab.getCrimes()

        val viewPageAdapter = DetailAdapter(crimeList, this)
        viewPager2 = findViewById(R.id.detail_view_pager)
        viewPager2.adapter = viewPageAdapter
        viewPager2.setCurrentItem(position, false)

    }
}