package com.example.zad_10_06_krajestolice.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zad_10_06_krajestolice.R
import com.example.zad_10_06_krajestolice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}