package com.example.zad_09_03_galleryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zad_09_03_galleryapp.adapter.GalleryAdapter
import com.example.zad_09_03_galleryapp.database.DBHandler
import com.example.zad_09_03_galleryapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnAddElement.setOnClickListener {
            val intent = Intent(this, SingleItemActivity::class.java)
            startActivity(intent)
        }

        getAllItems()

    }

    private fun getAllItems() {
        val dbHandler = DBHandler(this)
        val itemList = dbHandler.getAllItems()
        if(itemList.size > 0) {
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            val adapter = GalleryAdapter(this, itemList)
            binding.recyclerView.adapter = adapter
        }
    }

}