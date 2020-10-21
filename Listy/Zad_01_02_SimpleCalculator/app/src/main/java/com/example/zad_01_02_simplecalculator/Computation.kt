package com.example.zad_01_02_simplecalculator

class Computation {

    public enum class Operator {ADD, SUB}

    fun add(firstOperand: Double, secondOperand: Double): Double {
        return firstOperand + secondOperand
    }

    fun sub(firstOperand: Double, secondOperand: Double): Double {
        return firstOperand - secondOperand
    }

}