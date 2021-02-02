package pl.cookiez.zad_99_01_projekt_stopwatch.view.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import pl.cookiez.zad_99_01_projekt_stopwatch.databinding.FragmentStopWatchDetailBinding
import pl.cookiez.zad_99_01_projekt_stopwatch.model.StopWatch
import pl.cookiez.zad_99_01_projekt_stopwatch.util.backgroundColor2hex
import pl.cookiez.zad_99_01_projekt_stopwatch.util.hex2background
import pl.cookiez.zad_99_01_projekt_stopwatch.util.nanoTime2strTimeHMS
import pl.cookiez.zad_99_01_projekt_stopwatch.view.master.StopWatchesListAdapter
import pl.cookiez.zad_99_01_projekt_stopwatch.viewmodel.StopWatchDetailViewModel
import kotlin.math.log


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

    // show play or pause&stop
    private fun showCorrectUi(repeatCount: Int = 50, delay: Long = 100) {
        Log.d("zaq1", "stopWatchIsCounting: ${viewModel.stopWatch.value?.stopWatchIsCounting}")
        if (repeatCount >= 0 && viewModel.stopWatch.value?.stopWatchIsCounting == null) {
            Handler(Looper.getMainLooper())
                .postDelayed({ showCorrectUi(repeatCount-1, delay) }, delay)
        } else if (repeatCount < 0) {
            // timeout
        } else {
            if (viewModel.stopWatch.value!!.stopWatchIsCounting == true) {
                visibilityPlay()
            } else {
                visibilityPause()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments.let {
            stopWatchUUID = StopWatchDetailArgs.fromBundle(it!!).stopwatchUUID
        }
        viewModel.refresh()
        viewModel.fetch(stopWatchUUID)
        observeViewModel()
        showCorrectUi()
        if (!handlingStarted) {
            handlingStarted = true
            tickTime()
        }
        binding.stopwatchTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // save this stopwatch to db
                val stopWatch = binding.stopwatch
                stopWatch?.title = s.toString()
                if (stopWatch != null)
                    viewModel.updateStopWatch(stopWatch)
            }
        })
        // play
        binding.stopwatchControlsPlay.setOnClickListener { play() }
        // pause
        binding.stopwatchControlsPause.setOnClickListener { pause() }
        // reset (stop)
        binding.stopwatchControlsStop.setOnClickListener { stop() }
        // delete
        binding.stopwatchControlsDelete.setOnClickListener {
            viewModel.deleteStopWatch(binding.stopwatch!!)
            handling = false
            adapter.notifyDataSetChanged()
            val action = StopWatchDetailDirections
                .actionStopWatchDetailToStopWatchesList()
            Navigation.findNavController(view).navigate(action)
        }
        // change colors
        // clear color
        binding.colorClear.setOnClickListener {
            if (binding.stopwatch != null){
                binding.stopwatch!!.backgroundColor = null
                binding.stopwatch!!.backgroundUrl = null
                viewModel.updateStopWatch(binding.stopwatch!!)
                changeColorOfBg()
                adapter.notifyDataSetChanged()
            }
        }
        // change color
        binding.colorRed.setOnClickListener { changeColorToDb(it.background) }
        binding.colorGreen.setOnClickListener { changeColorToDb(it.background) }
        binding.colorYellow.setOnClickListener { changeColorToDb(it.background) }
        binding.colorBlue.setOnClickListener { changeColorToDb(it.background) }
        binding.colorPhoto.setOnClickListener {
            Toast
                .makeText(context, "Not implemented yet", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun changeColorToDb(background: Drawable) {
        if (background is ColorDrawable && binding.stopwatch != null) {
            val str = backgroundColor2hex(background)
            binding.stopwatch!!.backgroundColor = str
            viewModel.updateStopWatch(binding.stopwatch!!)
            changeColorOfBg()
            adapter.notifyDataSetChanged()
        }
    }

    private fun changeColorOfBg() {
        if (binding.stopwatch?.backgroundColor != null) {
            val background = hex2background(binding.stopwatch!!.backgroundColor!!)
            binding.constraintLayoutMain.background = background
        } else if (binding.stopwatch != null) {
            binding.constraintLayoutMain.background = ColorDrawable(Color.TRANSPARENT)
        }
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
                viewModel.stopWatch.value!!.timeStr = strTime
                viewModel.updateStopWatch(viewModel.stopWatch.value!!)
                adapter.notifyItemChanged(viewModel.stopWatch.value!!.position!!)
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