package com.example.zad_10_05_mvvm_localroom.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zad_10_05_mvvm_localroom.R
import com.example.zad_10_05_mvvm_localroom.utils.WordApplication
import com.example.zad_10_05_mvvm_localroom.viewmodel.WordViewModel
import com.example.zad_10_05_mvvm_localroom.viewmodel.WordViewModelFactory

class MainActivity : AppCompatActivity() {

    private val wordViewModel : WordViewModel by viewModels {
        WordViewModelFactory((application as WordApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = WordRecyclerViewAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        wordViewModel.allWords.observe(this, { words ->
            words?.let{ adapter.submitList(it) }
        })

    }
}