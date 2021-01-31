package pl.cookiez.zad_99_01_projekt_stopwatch.view.master

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import pl.cookiez.zad_99_01_projekt_stopwatch.R
import pl.cookiez.zad_99_01_projekt_stopwatch.databinding.FragmentStopWatchesListBinding
import pl.cookiez.zad_99_01_projekt_stopwatch.model.StopWatch
import pl.cookiez.zad_99_01_projekt_stopwatch.model.local.StopWatchRoom
import pl.cookiez.zad_99_01_projekt_stopwatch.viewmodel.StopWatchesListViewModel


class StopWatchesList : Fragment() {

    private var _binding: FragmentStopWatchesListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StopWatchesListViewModel by viewModels()
    private val adapter: StopWatchesListAdapter = StopWatchesListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStopWatchesListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.refresh()
        binding.stopwatchRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = this@StopWatchesList.adapter

            observeViewModel()
        }
        // add Stopwatch
        binding.stopwatchAddNew.setOnClickListener {
            val newStopwatch = StopWatch(
                null,
                "",
                "null",
                "null",
                System.nanoTime(),
                0L,
                true
            )
            viewModel.stopWatchesList.value = viewModel.stopWatchesList.value?.plus(newStopwatch)
            viewModel.stopWatchesList.value?.let { adapter.updateList(it) }
            viewModel.stopWatchesList.value?.let { viewModel.insertToLocal(it) }
            adapter.notifyDataSetChanged()
        }
    }

    override fun onPause() {
        super.onPause()
        // stop time ticks
        adapter.handling = false
        adapter.handlingStarted = false
    }

    private fun observeViewModel() {
        viewModel.stopWatchesList.observe(viewLifecycleOwner, { stopWatchesList ->
            stopWatchesList.let {
                binding.stopwatchRecyclerView.visibility = View.VISIBLE
                adapter.updateList(stopWatchesList)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}