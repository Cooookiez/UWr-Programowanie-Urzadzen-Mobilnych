package com.example.zad_02_06_physicsquizextended

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_question.*

class AddQuestion : AppCompatActivity() {

    var newQuestion: Question? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_question)

        btnQuestionAdd.setOnClickListener {

            val selectedId: Int = radioGroupAnswer.checkedRadioButtonId
            val checkedRadio = findViewById<RadioButton>(selectedId)

            val strOdp = checkedRadio.text.toString()
            var bOdp: Boolean = false

            bOdp = when(strOdp.toLowerCase().trim()) {
                "true" -> true
                "false" -> false
                else -> false
            }

            if (newQuestionText.text.toString().isEmpty()){
                val toast = Toast.makeText(this, "Puste Pole!", Toast.LENGTH_LONG)
                toast.show()
            } else {
                newQuestion = Question(newQuestionText.text.toString(), bOdp)
                goBackAndAddQuestion()
            }


        }
    }

    fun goBackAndAddQuestion() {
        val replyIntent: Intent = Intent()
        replyIntent.putExtra(MainActivity.EXTRA_ADD_QUESTION, newQuestion)
        setResult(RESULT_OK, replyIntent)
        finish()
    }

}