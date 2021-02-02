package pl.cookiez.zad_99_01_projekt_stopwatch.view.master

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import pl.cookiez.zad_99_01_projekt_stopwatch.R
import pl.cookiez.zad_99_01_projekt_stopwatch.databinding.FragmentStopWatchesListBinding
import pl.cookiez.zad_99_01_projekt_stopwatch.model.StopWatch
import pl.cookiez.zad_99_01_projekt_stopwatch.util.hex2background
import pl.cookiez.zad_99_01_projekt_stopwatch.viewmodel.StopWatchesListViewModel


class StopWatchesList : Fragment() {

    private var _binding: FragmentStopWatchesListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StopWatchesListViewModel by viewModels()
    private val adapter: StopWatchesListAdapter = StopWatchesListAdapter(arrayListOf())
    private var colorOrImageLoaded = false

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
                viewModel.stopWatchesList.value!!.size,
                null,
                null,
                null,
                System.nanoTime(),
                0L,
                "00:00",
                true
            )
            viewModel.stopWatchesList.value = viewModel.stopWatchesList.value?.plus(newStopwatch)
            viewModel.stopWatchesList.value?.let { adapter.updateList(it) }
            viewModel.stopWatchesList.value?.let { viewModel.insertToLocal(it) }
            adapter.notifyDataSetChanged()
        }
        // load color
    }

    private fun waitForViewToLoad(view: View, repeatCount: Int = 50, delay: Long = 100) {
        when {
            repeatCount >= 0 -> {
                Handler(Looper.getMainLooper())
                        .postDelayed({ waitForViewToLoad(view, repeatCount - 1, delay) }, delay)
            }
            repeatCount < 0 -> {
                // timeout
            }
            else -> {
//                setBg()
                // TODO: 2/2/21 zmienić by było w odpowiednich funkcjach
                colorOrImageLoaded = true
//                view.background = Drawable(Color.parseColor("#ff00ff"))
//                val layoutMain =
//                        view.findViewById<LinearLayout>(R.id.layout_stopwatch_list_single_item)
//                layoutMain.background = hex2background("#ff00ff")
            }
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