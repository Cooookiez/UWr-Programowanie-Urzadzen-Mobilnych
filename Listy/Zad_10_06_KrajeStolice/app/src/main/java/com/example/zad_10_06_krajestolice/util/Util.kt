package com.example.zad_10_06_krajestolice.util

import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.zad_10_06_krajestolice.R

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 20f
        start()
    }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {

    Log.d("zaq1", "Uri: $uri")
    Log.d("zaq1", "progressDrawable: $progressDrawable")

    val option = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.physicyst)

    Glide.with(context)
        .setDefaultRequestOptions(option)
        .load(uri)
        .into(this)
}