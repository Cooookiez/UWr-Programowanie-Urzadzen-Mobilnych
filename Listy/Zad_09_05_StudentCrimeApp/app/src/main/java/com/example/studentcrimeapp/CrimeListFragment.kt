package com.example.studentcrimeapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CrimeListFragment : Fragment() {

    private lateinit var mAdapter: CrimeRecyclerAdapter

    private lateinit var recyclerCrimeView: RecyclerView
    private lateinit var floatingBtnReportACrime: FloatingActionButton
    private lateinit var editTextSearchBar: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View =
            inflater.inflate(R.layout.fragment_crime_list, container, false)
        this.floatingBtnReportACrime = view.findViewById(R.id.floatingBtnReportACrime)
        this.floatingBtnReportACrime.setOnClickListener {
            CrimeLab.get(inflater.context)!!.addEmpty()
            this.mAdapter.notifyDataSetChanged()
        }

        this.editTextSearchBar = view.findViewById(R.id.editTextSearchBar)
        this.editTextSearchBar.setText(CrimeLab[inflater.context]?.getSearch())
        this.editTextSearchBar.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                CrimeLab[inflater.context]?.setSearch(s.toString())
                CrimeLab[inflater.context]?.reLoad()
                mAdapter.notifyDataSetChanged();
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

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