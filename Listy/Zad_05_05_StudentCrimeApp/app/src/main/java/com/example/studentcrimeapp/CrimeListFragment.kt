package com.example.studentcrimeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CrimeListFragment : Fragment() {
    private lateinit var crimeRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private val crimeItemListener = CrimesAdapter.OnClickListener{

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecyclerView = view.findViewById(R.id.recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)

        val crimeList: List<Crime> = CrimeLab.getCrimes()
        val recyclerView: RecyclerView = view!!.findViewById(R.id.recycler_view) as RecyclerView

        val adapter = CrimesAdapter(crimeList, crimeItemListener)
        recyclerView.adapter = adapter

        return view
    }
}