package com.example.studentcrimeapp

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment : DialogFragment() {

    companion object {
        private val TIME_ARG = "time"
        val TIME_EXTRA = "Time"

        fun newInstance(date: Date): TimePickerFragment {
            val args: Bundle = Bundle()
            args.putSerializable(TIME_ARG, date)

            val fragment: TimePickerFragment = TimePickerFragment()
            fragment.arguments = args

            return fragment
        }
    }

    private lateinit var timePicker: TimePicker

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return super.onCreateDialog(savedInstanceState)
        val date: Date = arguments?.getSerializable(TIME_ARG) as Date

        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date

        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)

        val view: View = LayoutInflater.from(context).inflate(R.layout.time_picker_dial, null)

        this.timePicker = view.findViewById(R.id.time_picker_dialog)
        this.timePicker.hour = hour
        this.timePicker.minute = minute

        return AlertDialog.Builder(activity!!)
            .setView(view)
            .setTitle("Crime Time")
            .setPositiveButton(
                android.R.string.ok,
                DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                    val date: Date = GregorianCalendar(
                        0, 0, 0,
                        this.timePicker.hour,
                        this.timePicker.minute
                    ).time
                    sendResult(Activity.RESULT_OK, date)
                }
            )
            .create()
    }

    private fun sendResult(resultCode: Int, date: Date) {
        if (targetFragment == null) {
            return
        } else {
            val intent: Intent = Intent()
            intent.putExtra(TIME_EXTRA, date)
            targetFragment!!.onActivityResult(
                targetRequestCode,
                resultCode,
                intent
            )
        }
    }
}