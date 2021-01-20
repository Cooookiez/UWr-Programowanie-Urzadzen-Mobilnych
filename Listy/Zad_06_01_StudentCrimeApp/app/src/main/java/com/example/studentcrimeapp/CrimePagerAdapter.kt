package com.example.studentcrimeapp

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class CrimePagerAdapter : RecyclerView.Adapter<CrimePagerAdapter.ViewHolder> {

    companion object {
        private val DATE_DIALOG: String = "DATE"
        private val DATE_REQUEST: Int = 0
        private val TIME_DIALOG: String = "TIME"
        private val TIME_REQUEST: Int = 1
    }

    private lateinit var mContext: Context
    private lateinit var mParent: CrimeFragment

    constructor(context: Context, parent: CrimeFragment) {
        this.mContext = context
        this.mParent = parent
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.fragment_crime, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val title: String = CrimeLab[mContext]!!.getCrimes()[holder.adapterPosition].getTitle()
        val date: Date = CrimeLab[mContext]!!.getCrimes()[holder.adapterPosition].getDate()
        val solved: Boolean = CrimeLab[mContext]!!.getCrimes()[holder.adapterPosition].getSolved()


        // Title change and save
        holder.crime_title.setText(title)           // ni mam pojecia czemu tu nie dziala ".text = "
        holder.crime_title.addTextChangedListener(object: TextWatcher {
            // TODO("Ni wiem czemu mi ni dziala (nie zapisuje sie lokalnie? (nie db))")
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                CrimeLab[mContext]!!.getCrimes()[holder.adapterPosition].setTitle(s.toString())
                //CrimeLab[mContext]!!.getCrimes()[holder.adapterPosition].setSolvedTo(isChecked)
                CrimeLab[mContext]!!.updateAtIndexOf(holder.adapterPosition)
            }

        })

        // Date change and save
        holder.crime_date.text = date.toString()
        holder.crime_date.setOnClickListener {
            val fragmentManager: FragmentManager? = this.mParent.fragmentManager
            val dialog: DatePickerFragment =
                DatePickerFragment.newInstance( CrimeLab[this.mContext]!!
                    .getCrimes()[holder.adapterPosition].getDate() )
            dialog.setTargetFragment(this.mParent, DATE_REQUEST)
            dialog.show(fragmentManager!!, DATE_DIALOG)
        }

        // Time change and save
        val hours = if (date.hours < 10) {
            "0${date.hours}"
        } else {
            "${date.hours}"
        }
        val minutes = if (date.minutes < 10) {
            "0${date.minutes}"
        } else {
            "${date.minutes}"
        }
        holder.crime_time.text = "$hours:$minutes"
        holder.crime_time.setOnClickListener {
            val fragmentManager: FragmentManager? = this.mParent.fragmentManager
            val dialog: TimePickerFragment =
                TimePickerFragment.newInstance( CrimeLab[this.mContext]!!
                    .getCrimes()[holder.adapterPosition].getDate() )
            dialog.setTargetFragment(this.mParent, TIME_REQUEST)
            dialog.show(fragmentManager!!, TIME_DIALOG)
        }

        // Crime solved status
        holder.crime_solved.isChecked = solved
        holder.crime_solved.setOnCheckedChangeListener { _, isChecked ->
            CrimeLab[mContext]!!.getCrimes()[holder.adapterPosition].setSolvedTo(isChecked)
            CrimeLab[mContext]!!.updateAtIndexOf(holder.adapterPosition)
        }

        // Delete crime
        holder.btnDelete.setOnClickListener {
            CrimeLab[mContext]!!.removeAtIndexOf(holder.adapterPosition)
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int { return CrimeLab.get(mContext)!!.getCrimes().size }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var crime_title: EditText
        var crime_date: Button
        var crime_time: Button
        var crime_solved: CheckBox
        var btnDelete: Button

        init {
            crime_title = itemView.findViewById(R.id.crime_title)
            crime_date = itemView.findViewById(R.id.crime_date)
            crime_time = itemView.findViewById(R.id.crime_time)
            crime_solved = itemView.findViewById(R.id.crime_solved)
            btnDelete = itemView.findViewById(R.id.btnDelete)
        }
    }

}