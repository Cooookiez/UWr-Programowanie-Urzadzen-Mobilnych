package com.example.zad_02_06_physicsquizextended

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_QUESTION: String = "com.example.zad_02_06_PhysicsQuizExtended.extra.QUESTION"
        const val EXTRA_ADD_QUESTION: String = "com.example.zad_02_06_PhysicsQuizExtended.extra.ADD_QUESTION"
        const val CHEATED_ANSWER = 1
        const val RESULT = 2
        const val ADD_QUESTION = 3
    }

    private var questionBank = arrayListOf(
        Question("Earth is flat", false),
        Question("Fire has shadow", false),
        Question("Black is effect of cobain all colors", false),
        Question("There is no complete vacuum in space", true),
        Question("Earth is in centaur of solar system", false),
    )

    private var curIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            curIndex = savedInstanceState.getInt("curIndex")
            val numberOfQuestions: Int = savedInstanceState.getInt("numberOfQuestions")
            var questionBankTMP = arrayListOf<Question>()
            for (i in 0 until numberOfQuestions) {
                questionBankTMP.add(savedInstanceState.getParcelable<Question>("questionBank_$i") as Question)
            }
            questionBank = questionBankTMP
            updateQuestion()
        }

        updateQuestion()

        btnOdpTrue.setOnClickListener { checkAnswer(true) }
        btnOdpFalse.setOnClickListener { checkAnswer(false) }

        btnCheat.setOnClickListener {
            val intent: Intent = Intent(this, Cheater::class.java)
            intent.putExtra(EXTRA_QUESTION, questionBank[curIndex])
            intent.putExtra("numberOfQuestions", questionBank.size)
            for (i in questionBank.indices){
                intent.putExtra("questionBank_$i", questionBank[i])
            }
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

        btnShowCorrectAnswer.setOnClickListener {
            val odp: String = "Poprawna odpowiedź: " + questionBank[curIndex].getAnswer().toString()
            var toast = Toast.makeText(this, odp, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 256)
            toast.show()
        }

    }

    private fun checkAnswer(usrAns: Boolean) {
        val correctAns = questionBank[curIndex].getAnswer()
        questionBank[curIndex].setAnswered(true)
        if (usrAns == correctAns) {
            questionBank[curIndex].setCorrectAnswer(true)
        }
        updateQuestion()
    }

    private fun updateQuestion() {
        questionTextShow.text = questionBank[curIndex].getText()
        questionCurMaxNumber.text = (curIndex+1).toString() + " / " + questionBank.size.toString()

        if (isEveryQuestionAnswered()) {
            showQuizResult()
        }

        // buttons visibility
        if (questionBank[curIndex].getAnswered()!!) {
            btnOdpFalse.visibility = View.INVISIBLE
            btnOdpTrue.visibility = View.INVISIBLE
            btnShowCorrectAnswer.visibility = View.VISIBLE
            answerTips.visibility = View.INVISIBLE
        } else {
            btnOdpFalse.visibility = View.VISIBLE
            btnOdpTrue.visibility = View.VISIBLE
            btnShowCorrectAnswer.visibility = View.INVISIBLE
            answerTips.visibility = View.VISIBLE
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
        startActivityForResult(intent, RESULT)
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
                val reset = data?.getBooleanExtra(QuizResult.EXTRA_RESET, false)
                if (reset == true) {
                    Log.d("jakiesRzeczy", "if")
                    for(i in 0 until questionBank.size) {
                        questionBank[i].reset()
                    }
                }
                curIndex = 0
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("curIndex", curIndex)
        outState.putInt("numberOfQuestions", questionBank.size)
        for(i in 0 until questionBank.size) {
            outState.putParcelable("questionBank_$i", questionBank[i])
        }
    }

}