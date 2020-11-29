package com.example.zad_05_02_pageview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2

class DetailActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val news: News = intent.getParcelableExtra("KEY")!!
        val newsList: List<News> = News.newsList(applicationContext)

        val viewPagerAdapter = DetailAdapter(newsList, this)
        viewPager2 = findViewById(R.id.detail_ciew_pager)
        viewPager2.adapter = viewPagerAdapter
        viewPager2.setCurrentItem(news.id, false)
    }
}