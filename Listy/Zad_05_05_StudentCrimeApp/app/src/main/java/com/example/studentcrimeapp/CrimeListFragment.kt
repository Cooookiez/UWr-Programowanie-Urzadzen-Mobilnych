package com.example.studentcrimeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class CrimeListFragment : Fragment() {
    private lateinit var crimeRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private val crimeItemListener = CrimesAdapter.OnClickListener{
        crime ->
        val crimeList: List<Crime> = CrimeLab.getCrimes()
        val position: Int = getCrimePositionFromID(crimeList, crime.id)
        val intent = Intent(activity, CrimeActivity::class.java)
        intent.putExtra("position", position)
        activity?.startActivity(intent)
    }

    private fun getCrimePositionFromID(crimeList: List<Crime>, id: UUID) : Int {
        var index: Int = 0
        var found: Boolean = false
        while (!found) {
            if (crimeList[index].id == id) {
                found = true
            } else {
                index++
            }
        }
        return index
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