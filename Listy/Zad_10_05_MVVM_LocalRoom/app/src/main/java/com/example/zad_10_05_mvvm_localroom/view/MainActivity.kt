package com.example.zad_10_05_mvvm_localroom.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zad_10_05_mvvm_localroom.R
import com.example.zad_10_05_mvvm_localroom.model.WordEntity
import com.example.zad_10_05_mvvm_localroom.utils.WordApplication
import com.example.zad_10_05_mvvm_localroom.viewmodel.WordViewModel
import com.example.zad_10_05_mvvm_localroom.viewmodel.WordViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val wordViewModel : WordViewModel by viewModels {
        WordViewModelFactory((application as WordApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.FAB)
        fab.setOnClickListener {
            val intent =
                Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, 0)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = WordRecyclerViewAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        wordViewModel.allWords.observe(this, { words ->
            words?.let{ adapter.submitList(it) }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra("REPLY")?.let { reply ->
                wordViewModel.insert(WordEntity(reply))
            }
        } else {
            Toast.makeText(this, "FAIL", Toast.LENGTH_SHORT).show()
        }
    }
}