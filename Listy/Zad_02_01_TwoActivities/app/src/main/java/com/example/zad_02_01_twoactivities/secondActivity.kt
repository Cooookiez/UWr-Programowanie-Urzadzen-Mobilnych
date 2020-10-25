package com.example.zad_02_01_twoactivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_second.*

class secondActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_REPLY = "com.example.zad_02_01_twoactivities.extra.REPLY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val intent = getIntent()
        val message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE)

        text_message.text = message

    }

    fun returnReply(view: View) {

        val reply = editText_second.text.toString()
        val replyIntent = Intent()

        replyIntent.putExtra(EXTRA_REPLY, reply)
        setResult(RESULT_OK, replyIntent)
        finish()
    }

}