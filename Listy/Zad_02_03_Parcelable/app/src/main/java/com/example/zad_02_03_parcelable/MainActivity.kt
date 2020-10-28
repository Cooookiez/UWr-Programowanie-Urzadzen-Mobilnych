package com.example.zad_02_03_parcelable

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var prop: Prop? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prop = Prop(1, 2, "HAHA")

        parcel_button?.setOnClickListener {
            var intent: Intent = Intent(this, ParcelActivity::class.java)
            intent.putExtra("ParcelProperty", prop)
            startActivity(intent)
        }

    }
}