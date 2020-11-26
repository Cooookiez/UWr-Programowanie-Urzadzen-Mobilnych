package com.example.zad_02_67_wifia

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c: Calendar = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog( requireActivity(), this, year, month, day )
    }

    override fun onDateSet(p0: DatePicker?, y: Int, m: Int, d: Int) {
        val activity: EventActivity = requireActivity() as EventActivity
        activity.processDatePickerResult(y, m, d)
    }

}