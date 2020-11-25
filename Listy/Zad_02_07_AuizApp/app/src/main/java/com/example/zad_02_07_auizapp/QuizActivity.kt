package com.example.zad_02_07_auizapp

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mmQuestionList: ArrayList<Question>? = null
    private var mSelectedAnswerPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        mmQuestionList = Questions.getQuestions()

        setQuestion()

        textViewAnswerOne.setOnClickListener(this)
        textViewAnswerTwo.setOnClickListener(this)
        textViewAnswerThree.setOnClickListener(this)
        buttonNext.setOnClickListener(this)
    }

    fun setQuestion() {
        val question = mmQuestionList!![mCurrentPosition - 1]
        changeToDefaultView()
        buttonNext.text = "Next"
        progressBar.progress = mCurrentPosition
        textViewProgress.text = "$mCurrentPosition / ${progressBar.max}"

        textViewQuestion.text = question.question
        imageViewQuestion.setImageResource(question.image)

        textViewAnswerOne.text = question.answerOne
        textViewAnswerTwo.text = question.answerTwo
        textViewAnswerThree.text = question.answerThree
    }

    fun changeToSelectedView(textView: TextView, selectedAns: Int) {
        changeToDefaultView()
        mSelectedAnswerPosition = selectedAns
        textView.setTypeface(textView.typeface, Typeface.BOLD)
        textView.background = ContextCompat.getDrawable(this,
            R.drawable.selected_answer_shape)
    }

    fun changeToDefaultView() {
        val answers = ArrayList<TextView>()
        answers.add(0, textViewAnswerOne)
        answers.add(1, textViewAnswerTwo)
        answers.add(2, textViewAnswerThree)

        for (answer in answers) {
            answer.setTextColor(Color.parseColor("#000000"))
            answer.typeface = Typeface.DEFAULT
            answer.background = ContextCompat.getDrawable(this,
            R.drawable.default_answer_shape)
        }
    }

    fun answerView(answer: Int, drawableItem: Int) {
        when(answer) {
            1 -> textViewAnswerOne.background = ContextCompat.getDrawable(this, drawableItem)
            2 -> textViewAnswerTwo.background = ContextCompat.getDrawable(this, drawableItem)
            3 -> textViewAnswerThree.background = ContextCompat.getDrawable(this, drawableItem)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.textViewAnswerOne -> changeToSelectedView(textViewAnswerOne, 1)
            R.id.textViewAnswerTwo -> changeToSelectedView(textViewAnswerTwo, 2)
            R.id.textViewAnswerThree -> changeToSelectedView(textViewAnswerThree, 3)
            R.id.buttonNext -> {
                if (mSelectedAnswerPosition == 0) {
                    mCurrentPosition++
                    if (mCurrentPosition <= mmQuestionList!!.size) {
                        setQuestion()
                    } else {
                        Toast.makeText(this, "Quiz Completed", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val question = mmQuestionList?.get(mCurrentPosition-1)
                    if (question!!.answerCorrect != mSelectedAnswerPosition) {
                        answerView(mSelectedAnswerPosition, R.drawable.incorrect_answer_shape)
                    }
                    answerView(question.answerCorrect, R.drawable.correct_answer_shape)
                    if (mCurrentPosition == mmQuestionList!!.size) {
                        buttonNext.text = "FINISH"
                    }
                    mSelectedAnswerPosition = 0
                }
            }
        }
    }

}