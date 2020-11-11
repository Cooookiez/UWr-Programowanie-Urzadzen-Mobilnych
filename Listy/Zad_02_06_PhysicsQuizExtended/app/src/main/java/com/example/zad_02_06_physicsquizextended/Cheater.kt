package com.example.zad_02_06_physicsquizextended

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cheater.*

class Cheater : AppCompatActivity() {

    private var question: Question? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheater)

        question = intent.getParcelableExtra<Question>(MainActivity.EXTRA_QUESTION)

        cheatedQuestion.text = question?.getText()

        btnShowAns.setOnClickListener {

            val toast = Toast.makeText(this, "Cheater! Shame on U!!!", Toast.LENGTH_SHORT)
            toast.show()

            cheatedOdp.text = question?.getAnswer().toString()

            if ( question?.getAnswered() == false) {
                question!!.setCheated(true)
            }

        }

    }

    override fun onBackPressed() {
        val replyIntent: Intent = Intent()

        replyIntent.putExtra(MainActivity.EXTRA_QUESTION, question)
        setResult(RESULT_OK, replyIntent)
        super.onBackPressed()
    }

}