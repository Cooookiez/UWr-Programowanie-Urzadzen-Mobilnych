package com.example.studentcrimeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CrimeListFragment : Fragment() {

    private lateinit var mAdapter: CrimeRecyclerAdapter

    private lateinit var recyclerCrimeView: RecyclerView
    private lateinit var btnReportACrime: Button
    private lateinit var floatingBtnReportACrime: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =
            inflater.inflate(R.layout.fragment_crime_list, container, false)
        this.btnReportACrime = view.findViewById(R.id.btnReportACrime)
        this.btnReportACrime.setOnClickListener {
            CrimeLab.get(inflater.context)!!.addEmpty()
            this.mAdapter.notifyDataSetChanged()
        }
        this.recyclerCrimeView = view.findViewById(R.id.recyclerCrimeView)
        this.recyclerCrimeView.layoutManager = LinearLayoutManager(activity)
        this.mAdapter = CrimeRecyclerAdapter(inflater.context)
        this.recyclerCrimeView.adapter = this.mAdapter

        return view
    }

    override fun onResume() {
        super.onResume()
        this.mAdapter.notifyItemChanged(CrimeFragment.quit)
    }

}