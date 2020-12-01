package com.example.studentcrimeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DetailAdapter(
    private val crimeList: List<Crime>,
    private val activity: CrimeActivity
) : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.crime_title)
        private val date: TextView = itemView.findViewById(R.id.crime_date)
        private val solved: CheckBox = itemView.findViewById(R.id.crime_solved)
        fun bind(crime: Crime) {
            title.text = crime.title
            date.text = crime.date.toString()
            solved.isChecked = crime.isSolved
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_crime, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCrime: Crime = crimeList[position]
        holder.bind(currentCrime)
    }

    override fun getItemCount(): Int = crimeList.size

}