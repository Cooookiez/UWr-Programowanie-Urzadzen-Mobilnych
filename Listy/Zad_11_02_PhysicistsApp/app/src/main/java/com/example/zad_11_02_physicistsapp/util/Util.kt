package com.example.zad_11_02_physicistsapp.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.zad_11_02_physicistsapp.R

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 15f
        centerRadius = 60f
        start()
    }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val option = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.physicyst)

    Glide.with(context)
        .setDefaultRequestOptions(option)
        .load(uri)
        .into(this)
}