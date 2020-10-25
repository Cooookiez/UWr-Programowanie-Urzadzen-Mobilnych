package com.example.zad_01_03_calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClear(view: View) {
        inputOutputTextView.text = ""
    }

    fun onDigit(view: View) {
        inputOutputTextView.append((view as Button).text)
    }

    private fun isOperatorExist (value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            ( value.contains("+") || value.contains("-") || value.contains("*") || value.contains("/") )
        }
    }

    fun onOperator(view: View) {
        if ( !isOperatorExist(inputOutputTextView.text.toString()) ) {
            inputOutputTextView.append((view as Button).text)
        }
    }

    fun onResult(view: View) {
        var value = inputOutputTextView.text.toString()
        if (value.contains("+")){

        }
    }
}