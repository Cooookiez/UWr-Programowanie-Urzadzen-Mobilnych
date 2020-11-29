package com.example.zad_05_02_pageview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsList: List<News> = News.newsList(applicationContext)
        val recyclerView: RecyclerView = findViewById(R.id.recycle_view)

        val adapter = NewsAdapter(newsList, newsItemListener)

        recyclerView.adapter = adapter
    }

    private val newsItemListener = NewsAdapter.OnClickListener {
        news ->
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("KEY", news)
        startActivity(intent)
    }
}