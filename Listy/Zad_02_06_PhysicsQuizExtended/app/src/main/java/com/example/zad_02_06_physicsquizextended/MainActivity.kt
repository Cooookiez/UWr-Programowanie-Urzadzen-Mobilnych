package com.example.zad_02_06_physicsquizextended

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_QUESTION: String = "com.example.zad_02_06_PhysicsQuizExtended.extra.QUESTION"
        const val EXTRA_ADD_QUESTION: String = "com.example.zad_02_06_PhysicsQuizExtended.extra.ADD_QUESTION"
        const val CHEATED_ANSWER = 1
        const val RESULT = 2
        const val ADD_QUESTION = 3
    }

    private val questionBank = arrayListOf(
        Question("JEDEN TRUE", true),
        Question("DWA FALSE", false),
        Question("CZY FALSE", false),
        Question("CZTERY TRUE", true),
        Question("PIĘĆ FALSE", false),
    )

    private var curIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateQuestion()

        btnOdpTrue.setOnClickListener { checkAnswer(true) }

        btnOdpFalse.setOnClickListener { checkAnswer(false) }

        btnCheat.setOnClickListener {
            val intent: Intent = Intent(this, Cheater::class.java)
            intent.putExtra(EXTRA_QUESTION, questionBank[curIndex])
            startActivityForResult(intent, CHEATED_ANSWER)
        }

        btnNext.setOnClickListener {
            curIndex = (curIndex+1) % questionBank.size
            updateQuestion()
        }

        btnPrev.setOnClickListener {
            curIndex -= 1
            if (curIndex < 0) {
                curIndex = questionBank.size - 1
            }
            updateQuestion()
        }

        btnSearchWeb.setOnClickListener {

            val url: String = "https://www.google.com/search?q=" + questionBank[curIndex].getText()
            val webpage: Uri = Uri.parse(url)

            val intent: Intent = Intent(Intent.ACTION_VIEW, webpage)

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent)
            }
        }

        addQuestion.setOnClickListener {
            val intent: Intent = Intent(this, AddQuestion::class.java)
            startActivityForResult(intent, ADD_QUESTION)
        }

    }

    private fun checkAnswer(usrAns: Boolean) {
        val correctAns = questionBank[curIndex].getAnswer()
        questionBank[curIndex].setAnswered(true)
        if (usrAns == questionBank[curIndex].getAnswer()) {
            questionBank[curIndex].setCorrectAnswer(true)
        }
        updateQuestion()
    }

    private fun updateQuestion() {
        questionTextShow.text = questionBank[curIndex].getText()
        questionCurMaxNumber.text = (curIndex+1).toString() + " / " + questionBank.size.toString()

        // buttons visibility
        if (questionBank[curIndex].getAnswered()!!) {
            btnOdpFalse.visibility = View.INVISIBLE
            btnOdpTrue.visibility = View.INVISIBLE
        } else {
            btnOdpFalse.visibility = View.VISIBLE
            btnOdpTrue.visibility = View.VISIBLE
        }

        if (isEveryQuestionAnswered()) {
            showQuizResult()
        }
    }

    private fun isEveryQuestionAnswered(): Boolean {
        var everyQuestionAnswered = true

        for(i in questionBank.indices) {
            if (!questionBank[i].getAnswered()!!) {
                everyQuestionAnswered = false
                break
            }
        }

        return everyQuestionAnswered
    }

    private fun showQuizResult() {
        val intent: Intent = Intent(this, QuizResult::class.java)
        intent.putExtra("numberOfQuestions", questionBank.size)
        for (i in questionBank.indices){
            intent.putExtra("questionBank_$i", questionBank[i])
        }
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            CHEATED_ANSWER -> {
                val replyQuestion = data?.getParcelableExtra<Question>(EXTRA_QUESTION)
                val cheated = replyQuestion?.getCheated()
                if (cheated != null) { questionBank[curIndex].setCheated(cheated) }
            }
            RESULT -> {

            }
            ADD_QUESTION -> {
                val newQuestion = data?.getParcelableExtra<Question>(EXTRA_ADD_QUESTION)
                if (newQuestion != null) {
                    questionBank.add(newQuestion)
                }
            }
        }

        updateQuestion()
    }

}