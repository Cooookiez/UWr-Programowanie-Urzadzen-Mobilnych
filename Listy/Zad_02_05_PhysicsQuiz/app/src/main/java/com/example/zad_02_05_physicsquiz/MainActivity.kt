package com.example.zad_02_05_physicsquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mBttTrue: Button

    private val questionBank = listOf(
        Question(R.string.question1, false),
        Question(R.string.question2, true),
        Question(R.string.question3, true),
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBttTrue = findViewById(R.id.bttTrue)

        mBttTrue.setOnClickListener {
            checkAnswer(true)
        }

        bttFalse.setOnClickListener {
            checkAnswer(false)
        }

        bttNext.setOnClickListener {
            currentIndex = (currentIndex+1) % questionBank.size
            updateQuestion()
        }

    }

    private fun updateQuestion(){
        val questionTextResId = questionBank[currentIndex].textResId
        questionText.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer)
            R.string.ansCorrect
        else
            R.string.ansIncorrect

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

}