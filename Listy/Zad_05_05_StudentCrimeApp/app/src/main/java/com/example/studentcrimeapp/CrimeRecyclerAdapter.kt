package com.example.studentcrimeapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class CrimeRecyclerAdapter : RecyclerView.Adapter<CrimeRecyclerAdapter.ViewHolder> {

    private var mContext: Context

    constructor(context: Context) {
        this.mContext = context
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CrimeRecyclerAdapter.ViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_element, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CrimeRecyclerAdapter.ViewHolder, position: Int) {
        holder.crimeTitle.text =
            CrimeLab.get(this.mContext)!!.getCrimes()[position].getTitle()
        holder.crimeDate.text =
            CrimeLab.get(this.mContext)!!.getCrimes()[position].getDate().toString()
        if (CrimeLab.get(this.mContext)!!.getCrimes()[position].getSolved()) {
            holder.crimeSolved.visibility = View.INVISIBLE
        } else {
            holder.crimeSolved.visibility = View.VISIBLE
        }
        holder.parent.setOnClickListener {
            var activity: AppCompatActivity = this.mContext as AppCompatActivity
            var fragment: CrimeFragment = CrimeFragment(this.mContext)
            var bundle: Bundle = Bundle()
            val id = CrimeLab
                    .get(this.mContext)!!.getCrimes()[holder.adapterPosition]
                .getId().toString()
            bundle.putString("id", id)
            fragment.arguments = bundle
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.activity_fragment_container, fragment)
                .addToBackStack(null).commit()
        }
    }

    override fun getItemCount(): Int { return CrimeLab.get(this.mContext)!!.getCrimes().size }

    class ViewHolder : RecyclerView.ViewHolder {

        var parent: LinearLayout

        var crimeTitle: TextView
        var crimeDate: TextView
        var crimeSolved: ImageView

        constructor(itemView: View) : super(itemView) {
            parent = itemView.findViewById(R.id.linearLayoutCrimesList)

            crimeTitle = itemView.findViewById(R.id.crimeTitle)
            crimeDate = itemView.findViewById(R.id.crimeDate)
            crimeSolved = itemView.findViewById(R.id.crimeSolved)
        }

    }

}