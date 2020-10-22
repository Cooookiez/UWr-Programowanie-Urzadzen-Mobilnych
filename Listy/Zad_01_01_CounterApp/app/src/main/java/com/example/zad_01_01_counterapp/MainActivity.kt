package com.example.zad_01_01_counterapp

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    private var mCount: Int = 0
    private var mShowCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mShowCount = findViewById(R.id.show_count)

        if (savedInstanceState != null)
            mCount = savedInstanceState.getInt("counter_state")

        if (mShowCount != null)
            mShowCount!!.text = mCount.toString()
    }

    fun showToast(view: View) {
        val text = getString(R.string.general_kenobi)
        var toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0, 160)
        toast.show()
    }

    //@SuppressLint("SetTextI18")
    fun countUp(view: View) {
        mCount++
        if (mShowCount != null)
            mShowCount!!.text = mCount.toString()
    }

    fun countDown(view: View) {
        mCount--
        if (mShowCount != null)
            mShowCount!!.text = mCount.toString()
    }

    fun resetCounter(view: View) {
        mCount = 0
        if (mShowCount != null)
            mShowCount!!.text = mCount.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("counter_state", mCount)
    }
}