package com.example.zad_01_02_simplecalculator

class Computation {

    enum class Operator {ADD, SUB, TIM, DIV}

    fun add(firstOperand: Double, secondOperand: Double): Double {
        return firstOperand + secondOperand
    }

    fun sub(firstOperand: Double, secondOperand: Double): Double {
        return firstOperand - secondOperand
    }

    fun tim(firstOperand: Double, secondOperand: Double): Double {
        return firstOperand * secondOperand
    }

    fun div(firstOperand: Double, secondOperand: Double): Double {
        if (secondOperand == 0.0){
            throw IllegalArgumentException("You cannot divide by zero")
        }else{
            return firstOperand / secondOperand
        }
    }

}