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
    private var numberOfQuestion: Int = 0
    private var questionBank = arrayListOf<Question>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheater)


        question = intent.getParcelableExtra<Question>(MainActivity.EXTRA_QUESTION)
        numberOfQuestion = intent.getIntExtra("numberOfQuestions", 0)
        for (i in 0 until numberOfQuestion) {
            questionBank.add(intent.getParcelableExtra("questionBank_$i")!!)
        }

        if (savedInstanceState != null) {
            question = savedInstanceState.getParcelable<Question>("question")
            cheatedOdp.text = savedInstanceState.getString("cheatedOdp")
            val numberOfQuestions: Int = savedInstanceState.getInt("numberOfQuestions")
            var questionBankTMP = arrayListOf<Question>()
            for (i in 0 until numberOfQuestions) {
                questionBankTMP.add(savedInstanceState.getParcelable<Question>("questionBank_$i") as Question)
            }
            questionBank = questionBankTMP
        }

        cheatedQuestion.text = question?.getText()

        btnShowAns.setOnClickListener {

            val toast = Toast.makeText(this, "Cheater! Shame on U!!!", Toast.LENGTH_SHORT)
            toast.show()

            cheatedOdp.text = question?.getAnswer().toString()

            if ( question?.getAnswered() == false) {
                question!!.setCheated(true)
            }

        }

        btnGoBackToMain.setOnClickListener {
            saveToGoBack()
            finish()
        }

    }

    private fun saveToGoBack() {
        val replyIntent: Intent = Intent()
        replyIntent.putExtra(MainActivity.EXTRA_QUESTION, question)
        replyIntent.putExtra("numberOfQuestions", questionBank.size)
        for (i in 0 until questionBank.size) {
            replyIntent.putExtra("questionBank_$i", questionBank[i])
        }
        setResult(RESULT_OK, replyIntent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("question", question)
        outState.putString("cheatedOdp", cheatedOdp.text.toString())
        outState.putInt("numberOfQuestions", questionBank.size)
        for(i in 0 until questionBank.size) {
            outState.putParcelable("questionBank_$i", questionBank[i])
        }
    }

}