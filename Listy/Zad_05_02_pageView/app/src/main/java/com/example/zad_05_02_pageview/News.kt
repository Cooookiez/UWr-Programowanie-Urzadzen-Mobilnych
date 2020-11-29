package com.example.zad_05_02_pageview

import android.content.Context
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class News(
    val id: Int,
    val picture: Int,
    val info: String
) : Parcelable {
    companion object {
        fun newsList(context: Context): List<News> {
            return listOf(
                News(0, R.drawable.zos_stu, context.getString(R.string.zos_stu)),
                News(1, R.drawable.s_astr, context.getString(R.string.s_astr)),
                News(2, R.drawable.s_fiz, context.getString(R.string.s_fiz)),
                News(3, R.drawable.s_inf, context.getString(R.string.s_inf)),
                News(4, R.drawable.sz_dok, context.getString(R.string.sz_dok)),
                News(5, R.drawable.kontakt, context.getString(R.string.kontakt))
            )
        }
    }
}