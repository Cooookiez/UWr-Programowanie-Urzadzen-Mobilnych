package com.example.zad_02_07_auizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.WindowCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        buttonStart.setOnClickListener {
            if (editTextName.text.isEmpty() and false) { //TODO "and false" for easy programing
                Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, QuizActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

}