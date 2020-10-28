package com.example.zad_02_03_parcelable

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_parcel.*

class ParcelActivity : AppCompatActivity() {
    var result: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parcel)

        var prop = intent.getParcelableExtra<Prop>("ParcelProperty")

        if (prop != null) {
            result = (prop.getA()!! + prop.getB()!!).toString() + prop.getC()!!
        }

        textFromParcel?.text = result

    }
}