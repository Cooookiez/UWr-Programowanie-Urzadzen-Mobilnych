package com.example.zad_05_02_pageview

import android.content.Context
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class News(
    val id: Int,
    val picture: Int
) : Parcelable {
    companion object {
        fun newsList(context: Context): List<News> {
            return listOf(
                News(0, R.drawable.zos_stu),
                News(1, R.drawable.s_astr),
                News(2, R.drawable.s_fiz),
                News(3, R.drawable.s_inf),
                News(4, R.drawable.sz_dok),
                News(5, R.drawable.kontakt)
            )
        }
    }
}