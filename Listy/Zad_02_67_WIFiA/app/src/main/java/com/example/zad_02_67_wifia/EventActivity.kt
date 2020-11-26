package com.example.zad_02_67_wifia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_event.*

class EventActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        val spinner: Spinner = findViewById(R.id.label_spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.labels_array,
            android.R.layout.simple_spinner_item
        ).also{ adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this


        date_text.setOnClickListener {
            val newFragment: DialogFragment = DatePickerFragment()
            newFragment.show(supportFragmentManager, "datePicker")
        }

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val spinnerLabel: String = p0?.getItemAtPosition(p2).toString()
        Toast.makeText(this, spinnerLabel, Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
//        TODO("Not yet implemented")
    }

    fun processDatePickerResult(y: Int, m: Int, d: Int) {
        val strYear: String = y.toString()
        val strMonth: String = (m+1).toString()
        val strDay: String = d.toString()
        val dateMessage: String = "$strMonth / $strDay / $strYear"
        date_text.text = dateMessage
    }
}