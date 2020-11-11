package com.example.zad_02_06_physicsquizextended

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_quiz_result.*
import java.lang.Math.round


class QuizResult : AppCompatActivity() {

    private var numberOfQuestion = 0
    private var questionBank = arrayListOf<Question>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)

        numberOfQuestion = intent.getIntExtra("numberOfQuestions", 0)
        for (i in 0 until numberOfQuestion) {
            questionBank.add(intent.getParcelableExtra("questionBank_$i")!!)
        }

        countResult()

        btnReset.setOnClickListener {
            System.exit(0)
        }

    }

    private fun countResult() {

        var numberOfQuestions = 0
        var numberOCorrectQuestions = 0
        var numberOfCheatedQuestions = 0

        for (i in 0 until questionBank.size) {
            numberOfQuestions++
            if (questionBank[i].getCorrectAns()!!) { numberOCorrectQuestions++ }
            if (questionBank[i].getCheated()!!) { numberOfCheatedQuestions++ }
        }

        var percent = round((numberOCorrectQuestions / numberOfQuestions * 100).toDouble()).toInt()
        var subPercent = 15 * numberOfCheatedQuestions
        if (subPercent >= 100) {
            subPercent = 100
        }
        percent -= subPercent

        result.text = "$percent%"
        numberOfCorrectAnswers.text = "$numberOCorrectQuestions / $numberOfQuestions"
        numberOfCheatedAnswers.text = "$numberOfCheatedQuestions razy"
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }
}