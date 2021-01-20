package com.example.studentcrimeapp

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment() {

    companion object {
        private val DATE_ARG = "date"
        val DATE_EXTRA = "Date"

        fun newInstance(date: Date) : DatePickerFragment {
            val args: Bundle = Bundle()
            args.putSerializable(DATE_ARG, date)

            val fragment: DatePickerFragment = DatePickerFragment()
            fragment.arguments = args

            return fragment
        }
    }

    private lateinit var datePicker: DatePicker

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return super.onCreateDialog(savedInstanceState)
        val date: Date = arguments?.getSerializable(DATE_ARG) as Date

        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.date_picker_dialog, null)

        this.datePicker = view.findViewById(R.id.date_picker_dialog)
        this.datePicker.init(year, month, day, null)

        return AlertDialog.Builder(activity)
            .setView(view)
            .setTitle("Crime Date")
            .setPositiveButton(
                android.R.string.ok,
                DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                    val date: Date = GregorianCalendar(
                        datePicker.year,
                        datePicker.month,
                        datePicker.dayOfMonth
                    ).time
                    sendResult(Activity.RESULT_OK, date)
                }
            ).create()
    }

    private fun sendResult(resultCode: Int, date: Date) {
        if (targetFragment == null) {
            return
        } else {
            val intent: Intent = Intent()
            intent.putExtra(DATE_EXTRA, date)
            targetFragment!!.onActivityResult(
                targetRequestCode,
                resultCode,
                intent
            )
        }
    }

}