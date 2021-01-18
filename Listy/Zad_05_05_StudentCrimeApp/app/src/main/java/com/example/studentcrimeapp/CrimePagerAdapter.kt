package com.example.studentcrimeapp

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class CrimePagerAdapter : RecyclerView.Adapter<CrimePagerAdapter.ViewHolder> {

    private companion object {
        val DATE_DIALOG: String = "DATE"
        val DATE_REQUEST: Int = 0
        val TIME_DIALOG: String = "TIME"
        val TIME_REQUEST: Int = 1
    }

    private lateinit var mContext: Context

    constructor(context: Context) {
        this.mContext = context
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CrimePagerAdapter.ViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.fragment_crime, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CrimePagerAdapter.ViewHolder, position: Int) {

        val title: String = CrimeLab.get(mContext)!!.getCrimes()[holder.adapterPosition].getTitle()
        val date: Date = CrimeLab.get(mContext)!!.getCrimes()[holder.adapterPosition].getDate()
        val solved: Boolean = CrimeLab.get(mContext)!!.getCrimes()[holder.adapterPosition].getSolved()

        holder.crime_title.setText(title)           // ni mam pojecia czemu tu nie dziala ".text = "
        holder.crime_date.text = date.toString()
        holder.crime_date.isEnabled = false
        holder.crime_solved.isChecked = solved

        holder.crime_title.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                CrimeLab.get(mContext)!!.getCrimes()[holder.adapterPosition].setTitle(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) { }
        })

        holder.crime_solved.setOnCheckedChangeListener { _, isChecked ->
            CrimeLab.get(mContext)!!.getCrimes()[holder.adapterPosition].setSolvedTo(isChecked)
        }

        holder.btnDelete.setOnClickListener {
            CrimeLab.get(mContext)!!.getCrimes().drop(holder.adapterPosition)
        }

    }

    override fun getItemCount(): Int { return CrimeLab.get(mContext)!!.getCrimes().size }

    class ViewHolder : RecyclerView.ViewHolder {

        lateinit var crime_title: EditText
        lateinit var crime_date: Button
        lateinit var crime_solved: CheckBox
        lateinit var btnDelete: Button

        constructor(itemView: View) : super(itemView) {
            crime_title = itemView.findViewById(R.id.crimeTitle)
            crime_date = itemView.findViewById(R.id.crime_date)
            crime_solved = itemView.findViewById(R.id.crime_solved)
            btnDelete = itemView.findViewById(R.id.btnDelete)
        }
    }

}