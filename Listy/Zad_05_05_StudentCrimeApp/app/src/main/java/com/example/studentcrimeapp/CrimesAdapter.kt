package com.example.studentcrimeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CrimesAdapter(
        private val crimeList: List<Crime>,
        private val onClickListener: OnClickListener
) : RecyclerView.Adapter<CrimesAdapter.CrimesViewHolder>() {

    class CrimesViewHolder(
            itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        private val crimeTitle: TextView = itemView.findViewById(R.id.crimeTitle)
        private val crimeDate: TextView = itemView.findViewById(R.id.crimeDate)
        private val crimeSolved: ImageView = itemView.findViewById(R.id.crimeSolved)

        fun bind(crime: Crime, onClickListener: OnClickListener) {
            crimeTitle.text = crime.title
            crimeDate.text = crime.date.toString()
            crimeSolved.visibility = if (crime.isSolved) ImageView.INVISIBLE else ImageView.VISIBLE

            itemView.setOnClickListener {
                onClickListener.onClick(crime)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimesAdapter.CrimesViewHolder {
        return CrimesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_crime, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CrimesAdapter.CrimesViewHolder, position: Int) {
        val crime: Crime = crimeList[position]
        holder.bind(crime, onClickListener)
    }

    override fun getItemCount(): Int = crimeList.size

    class OnClickListener(val clickListener: (crime: Crime) -> Unit) {
        fun onClick(crime: Crime) = clickListener(crime)
    }

}