package com.example.zad_02_06_physicsquizextended

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_quiz_result.*
import java.lang.Math.round


class QuizResult : AppCompatActivity() {

    companion object {
        const val EXTRA_RESET: String = "com.example.zad_02_06_PhysicsQuizExtended.extra.RESET"
    }

    private var numberOfQuestion = 0
    private var questionBank = arrayListOf<Question>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)

        numberOfQuestion = intent.getIntExtra("numberOfQuestions", 0)
        for (i in 0 until numberOfQuestion) {
            questionBank.add(intent.getParcelableExtra("questionBank_$i")!!)
        }

        if (savedInstanceState != null) {
            numberOfQuestion = savedInstanceState.getInt("numberOfQuestion")
            val questionBankTMP = arrayListOf<Question>()
            for (i in 0 until numberOfQuestion) {
                questionBankTMP.add(savedInstanceState.getParcelable<Question>("questionBank_$i") as Question)
            }
            questionBank = questionBankTMP
        }

        countResult()

        btnReset.setOnClickListener {
            val replyIntent: Intent = Intent()
            replyIntent.putExtra(EXTRA_RESET, true)
            setResult(RESULT_OK, replyIntent)
            finish()
        }

    }

    private fun countResult() {

        var numberOfQuestions = 0.0
        var numberOCorrectQuestions = 0.0
        var numberOfCheatedQuestions = 0.0

        for (i in 0 until questionBank.size) {
            numberOfQuestions++
            if (questionBank[i].getCorrectAns()!!) { numberOCorrectQuestions++ }
            if (questionBank[i].getCheated()!!) { numberOfCheatedQuestions++ }
        }

        var percent = round((numberOCorrectQuestions / numberOfQuestions * 100).toDouble()).toDouble()
        var subPercent = 15 * numberOfCheatedQuestions
        if (subPercent >= 100) {
            subPercent = 100.0
        }
        percent -= subPercent

        if (percent < 0.0) percent = 0.0

        result.text = "${percent.toInt()}%"
        numberOfCorrectAnswers.text = "${numberOCorrectQuestions.toInt()} / ${numberOfQuestions.toInt()}"
        numberOfCheatedAnswers.text = "${numberOfCheatedQuestions.toInt()} razy"
    }

    override fun onBackPressed() {} // by nie można się było cować

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("numberOfQuestion", numberOfQuestion)
        for(i in 0 until questionBank.size) {
            outState.putParcelable("questionBank_$i", questionBank[i])
        }
    }
}