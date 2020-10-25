package com.example.zad_02_01_twoactivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MESSAGE: String = "com.example.zad_02_01_twoactivities.extra.MESSAGE"
        const val TEXT_REQUEST = 1
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text_head_main?.visibility = View.INVISIBLE
    }

    fun launchSecondActivity(view: View) {

        val message: String = editText_main.text.toString()
        val intent = Intent(this, secondActivity::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        startActivityForResult(intent, TEXT_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val reply = data?.getStringExtra(secondActivity.EXTRA_REPLY)
        text_message_recived.text = reply
        text_head_main?.visibility = View.VISIBLE
    }
}