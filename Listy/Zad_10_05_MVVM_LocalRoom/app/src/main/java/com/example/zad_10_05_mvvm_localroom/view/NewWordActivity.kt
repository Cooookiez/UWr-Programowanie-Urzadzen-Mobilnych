package com.example.zad_10_05_mvvm_localroom.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.example.zad_10_05_mvvm_localroom.R

class NewWordActivity : AppCompatActivity() {

    private lateinit var editTextWord: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)

        editTextWord = findViewById(R.id.edit_text)

        val button = findViewById<Button>(R.id.btn_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editTextWord.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = editTextWord.text.toString()
                replyIntent.putExtra("REPLY", word)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

    }
}