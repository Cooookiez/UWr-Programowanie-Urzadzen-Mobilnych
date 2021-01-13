package com.example.zad_08_02_zadanie_2

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private companion object {
        // z tego co wiem taki zapis
        // równa się zapisowi
        // private static final int COUNT_UP_TO = 10
        // z Javy
        const val COUNT_UP_TO: Int = 10
    }

    private lateinit var countBtn: Button
    private lateinit var countTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countBtn = findViewById(R.id.countBtn)
        countTextView = findViewById(R.id.countTextView)

        countBtn.setOnClickListener { count() }
    }

    @SuppressLint("SetTextI18n")
    private fun count() {
        countBtn.isEnabled = false
        countBtn.text = "Counting"
        countTextView.text = "0"
        Thread {
            var local_count: Int = 0
            while (local_count <= COUNT_UP_TO) {
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                local_count++
                runOnUiThread {
                    countTextView.text = local_count.toString()
                }
            }
            runOnUiThread {
                countBtn.isEnabled = true
                countBtn.text = "Count Again"
                countTextView.text = "DONE!"
            }
        }.start()
    }
}