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
    private var mBttRes: Button? =  null
    private var mBttOperationCur: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mCalc = Computation()
        mResult = findViewById(R.id.operation_res_textView)
        mOperandFirstInput = findViewById(R.id.first_operand)
        mOperandSecondInput = findViewById(R.id.second_operand)
        val mBttAdd: Button? = findViewById(R.id.operation_add)
        val mBttSub: Button? = findViewById(R.id.operation_sub)
        val mBttTim: Button? = findViewById(R.id.operation_tim)
        val mBttDiv: Button? = findViewById(R.id.operation_div)
        mBttRes = findViewById(R.id.operation_res)
        mBttOperationCur = findViewById(R.id.operation_cur)

        if(savedInstanceState != null) {
            mResult?.text = savedInstanceState.getString("mResult")
            mBttOperationCur?.text = savedInstanceState.getString("mBttOperation_cur_str")
            mOperator = savedInstanceState!!.getSerializable("mOperator") as Computation.Operator
        }

        mBttAdd?.setOnClickListener {
            mOperator = Computation.Operator.ADD
            showCurOperator(mBttAdd)
        }
        mBttSub?.setOnClickListener {
            mOperator = Computation.Operator.SUB
            showCurOperator(mBttSub)
        }

        mBttTim?.setOnClickListener {
            mOperator = Computation.Operator.TIM
            showCurOperator(mBttTim)
        }
        mBttDiv?.setOnClickListener {
            mOperator = Computation.Operator.DIV
            showCurOperator(mBttDiv)
        }

        val mBttResLis = mBttRes
        mBttResLis?.setOnClickListener {
            if(mOperator != null)
                compute(mOperator!!)
        }
    }

    private fun showCurOperator(view: Button){
        mBttOperationCur?.text = view.text
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
            Computation.Operator.TIM -> mCalc?.tim(operandOne, operandTwo).toString()
            Computation.Operator.DIV -> mCalc?.div(operandOne, operandTwo).toString()
            else -> "SOME ERROR"
        }

        mResult?.text = result

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("mResult", mResult?.text.toString())
        outState.putString("mBttOperation_cur_str", mBttOperationCur?.text.toString())
        outState.putSerializable("mOperator", mOperator) // <- "Bundle" z Stackoverfflow
    }

}

