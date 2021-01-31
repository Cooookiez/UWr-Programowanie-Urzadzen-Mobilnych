package pl.cookiez.zad_99_01_projekt_stopwatch.view.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import pl.cookiez.zad_99_01_projekt_stopwatch.databinding.FragmentStopWatchDetailBinding
import pl.cookiez.zad_99_01_projekt_stopwatch.util.nanoTime2strTimeHMS
import pl.cookiez.zad_99_01_projekt_stopwatch.view.master.StopWatchesListAdapter
import pl.cookiez.zad_99_01_projekt_stopwatch.view.master.StopWatchesListDirections
import pl.cookiez.zad_99_01_projekt_stopwatch.viewmodel.StopWatchDetailViewModel


class StopWatchDetail : Fragment() {

    private var _binding: FragmentStopWatchDetailBinding? = null
    private val binding get() = _binding!!
    private var stopWatchUUID = 0L
    private val viewModel: StopWatchDetailViewModel by viewModels()
    private val adapter: StopWatchesListAdapter = StopWatchesListAdapter(arrayListOf())

    var handlingStarted: Boolean = false
    var handling: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStopWatchDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments.let {
            stopWatchUUID = StopWatchDetailArgs.fromBundle(it!!).stopwatchUUID
        }
        viewModel.refresh()
        viewModel.fetch(stopWatchUUID)
        observeViewModel()
        if (!handlingStarted) {
            handlingStarted = true
            tickTime()
        }
        if (binding.stopwatch?.stopWatchIsCounting == true) {
            visibilityPause()
        } else {
            visibilityPlay()
        }
        binding.stopwatchTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // save this stopwatch to db
                val stopWatch = binding.stopwatch
                stopWatch?.title = s.toString()
                viewModel.updateStopWatch(stopWatch!!)
            }
        })
        // play
        binding.stopwatchControlsPlay.setOnClickListener(View.OnClickListener { play() })
        // pause
        binding.stopwatchControlsPause.setOnClickListener(View.OnClickListener { pause() })
        // reset (stop)
        binding.stopwatchControlsStop.setOnClickListener(View.OnClickListener { stop() })
        // delete
        binding.stopwatchControlsDelete.setOnClickListener(View.OnClickListener {
            Toast.makeText(context, "Not Implemented yet", Toast.LENGTH_SHORT).show()
            viewModel.deleteStopWatch(binding.stopwatch!!)
            handling = false
            adapter.notifyDataSetChanged()
            val action = StopWatchDetailDirections
                .actionStopWatchDetailToStopWatchesList()
            Navigation.findNavController(view).navigate(action)
        })
    }


    private fun tickTime(tickDelay: Long = 1000) {
        if (handling) {
            Handler(Looper.getMainLooper()).postDelayed({ tickTime(tickDelay) }, tickDelay)
            // on tick code
            if (binding.stopwatch?.stopWatchIsCounting == true) {
                val nanoTime =
                    binding.stopwatch?.timeSavedFromPreviousCounting
                    ?.plus(System.nanoTime() - binding.stopwatch?.timeStart!!)
                val strTime = nanoTime2strTimeHMS(nanoTime!!)
                binding.stopwatchCurTime.text = strTime
            }
        }
    }

    private fun play() {
        visibilityPlay()
        val stopWatch = binding.stopwatch!!
        stopWatch.timeStart = System.nanoTime()
        stopWatch.stopWatchIsCounting = true
        binding.stopwatch = stopWatch
        viewModel.updateStopWatch(stopWatch)
    }

    private fun pause() {
        visibilityPause()
        val stopWatch = binding.stopwatch!!
        stopWatch.timeSavedFromPreviousCounting =
            stopWatch.timeSavedFromPreviousCounting
                ?.plus(System.nanoTime() - stopWatch.timeStart!!)
        stopWatch.timeStart = 0L
        stopWatch.stopWatchIsCounting = false
        binding.stopwatch = stopWatch
        viewModel.updateStopWatch(stopWatch)
    }

    private fun stop() {
        visibilityStop()
        val stopWatch = binding.stopwatch!!
        stopWatch.timeStart = 0L
        stopWatch.timeSavedFromPreviousCounting = 0L
        stopWatch.stopWatchIsCounting = false
        binding.stopwatch = stopWatch
        viewModel.updateStopWatch(stopWatch)
    }

    private fun visibilityPlay() {
        binding.stopwatchControlsPlay.visibility = View.GONE
        binding.stopwatchControlsPause.visibility = View.VISIBLE
        binding.stopwatchControlsStop.visibility = View.GONE
    }

    private fun visibilityPause() {
        binding.stopwatchControlsPlay.visibility = View.VISIBLE
        binding.stopwatchControlsPause.visibility = View.GONE
        binding.stopwatchControlsStop.visibility = View.VISIBLE
    }

    private fun visibilityStop() {
        binding.stopwatchControlsPlay.visibility = View.VISIBLE
        binding.stopwatchControlsPause.visibility = View.GONE
        binding.stopwatchControlsStop.visibility = View.GONE
    }

    private fun observeViewModel() {
        viewModel.stopWatch.observe(viewLifecycleOwner, { stopWatch ->
            stopWatch?.let {
                binding.stopwatch = stopWatch
            }
        })
    }

    override fun onPause() {
        super.onPause()
        handling = false
        handlingStarted = false
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}