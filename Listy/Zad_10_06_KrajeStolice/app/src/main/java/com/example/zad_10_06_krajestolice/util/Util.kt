package com.example.zad_10_06_krajestolice.util

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.zad_10_06_krajestolice.R
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 20f
        start()
    }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    GlideToVectorYou
        .init()
        .with(context)
        // setPlaceHolder(loading, error)
        .setPlaceHolder(R.drawable.rainbow_flag, R.drawable.question_mark_flag)
        .load(Uri.parse(uri), this)
}