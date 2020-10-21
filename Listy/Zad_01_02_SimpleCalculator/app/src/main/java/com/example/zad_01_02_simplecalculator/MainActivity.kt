package com.example.zad_01_02_simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.util.Log

class MainActivity : AppCompatActivity() {

    private var mCalc: Computation? = null
    private var mOperator: Computation.Operator? = null

    private var mOperandFirstInput: EditText? = null
    private var mOperandSecondInput: EditText? = null
    private var mResult: TextView? = null
    private var mBttAdd: Button? = null
    private var mBttSub: Button? =  null
    private var mBttRes: Button? =  null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mCalc = Computation()
        mResult = findViewById(R.id.operation_res_textView)
        mOperandFirstInput = findViewById(R.id.first_operand)
        mOperandSecondInput = findViewById(R.id.second_operand)
        mBttAdd = findViewById(R.id.operation_add)
        mBttSub = findViewById(R.id.operation_sub)
        mBttRes = findViewById(R.id.operation_res)

        Log.d("onCreate", "testy")

        val mBttAddLis = mBttAdd
        mBttAddLis?.setOnClickListener {
            mOperator = Computation.Operator.ADD

            Log.d("onCreate", "add")
        }

        val mBttSubLis = mBttSub
        mBttSubLis?.setOnClickListener {
            mOperator = Computation.Operator.SUB

            Log.d("onCreate", "sub")
        }

        val mBttResLis = mBttRes
        mBttResLis?.setOnClickListener {

            Log.d("onCreate", "res")
            if(mOperator != null)
                compute(mOperator!!)
        }
    }

    private companion object{
        fun getOperand(operandEditText: EditText?): Double {
            return  operandEditText?.text.toString().toDouble()
        }
    }

    private fun compute(operator: Computation.Operator) {
        var operandOne: Double?
        var operandTwo: Double?

        operandOne = getOperand(mOperandFirstInput)
        operandTwo = getOperand(mOperandSecondInput)

        mResult?.textSize = 100F

        var result: String = when(operator){
            Computation.Operator.ADD -> mCalc?.add(operandOne, operandTwo).toString()
            Computation.Operator.SUB -> mCalc?.sub(operandOne, operandTwo).toString()
            else -> "SOME ERROR"
        }

        mResult?.text = result

    }

}

