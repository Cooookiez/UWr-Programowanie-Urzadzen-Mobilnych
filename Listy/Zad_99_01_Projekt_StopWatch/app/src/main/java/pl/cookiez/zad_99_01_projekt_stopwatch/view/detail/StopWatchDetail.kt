package pl.cookiez.zad_99_01_projekt_stopwatch.view.detail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import pl.cookiez.zad_99_01_projekt_stopwatch.R
import pl.cookiez.zad_99_01_projekt_stopwatch.databinding.FragmentStopWatchDetailBinding
import pl.cookiez.zad_99_01_projekt_stopwatch.viewmodel.StopWatchDetailViewModel


class StopWatchDetail : Fragment() {

    private var _binding: FragmentStopWatchDetailBinding? = null
    private val binding get() = _binding!!
    private var stopWatchUUID = 0L
    private val viewModel: StopWatchDetailViewModel by viewModels()

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
            Log.d("zaq1 – Detail", "uuid: $stopWatchUUID")
        }
        viewModel.fetch(stopWatchUUID)
        observeViewModel()
        binding.stopwatchTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                //TODO("Not yet implemented")
            }

        })
    }

    private fun observeViewModel() {
        viewModel.stopWatch.observe(viewLifecycleOwner, {stopWatch ->
            stopWatch?.let {
                binding.stopwatch = stopWatch
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}