package pl.cookiez.zad_99_01_projekt_stopwatch.view.detail

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import pl.cookiez.zad_99_01_projekt_stopwatch.databinding.FragmentStopWatchDetailBinding
import pl.cookiez.zad_99_01_projekt_stopwatch.model.StopWatch
import pl.cookiez.zad_99_01_projekt_stopwatch.util.backgroundColor2hex
import pl.cookiez.zad_99_01_projekt_stopwatch.util.hex2background
import pl.cookiez.zad_99_01_projekt_stopwatch.util.nanoTime2strTimeHMS
import pl.cookiez.zad_99_01_projekt_stopwatch.view.master.StopWatchesListAdapter
import pl.cookiez.zad_99_01_projekt_stopwatch.viewmodel.StopWatchDetailViewModel
import top.defaults.colorpicker.ColorPickerPopup
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import kotlin.math.log


class StopWatchDetail : Fragment() {

    companion object {
        private const val REQUEST_CAMERA = 0
        private const val REQUEST_GALLERY = 1
    }

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
    ): View {
        _binding = FragmentStopWatchDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    // show play or pause&stop on start
    private fun showCorrectUi(repeatCount: Int = 50, delay: Long = 100) {
        Log.d("zaq1", "stopWatchIsCounting: ${viewModel.stopWatch.value?.stopWatchIsCounting}")
        if (repeatCount >= 0 && viewModel.stopWatch.value?.stopWatchIsCounting == null) {
            Handler(Looper.getMainLooper())
                .postDelayed({ showCorrectUi(repeatCount-1, delay) }, delay)
        } else if (repeatCount < 0) {
            // timeout
        } else {
            changeColorOfBg()
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
            AlertDialog.Builder(context)
                    .setTitle("Are you sure?")
                    .setMessage("After deleting stopwatch, there is no way to get it back.")
                    .setPositiveButton("Delete after all") { _, _ ->
                        if (binding.stopwatch != null) {
                            viewModel.deleteStopWatch(binding.stopwatch!!)
                            handling = false
                            adapter.notifyDataSetChanged()
                            Navigation.findNavController(view).popBackStack()
                        } else {
                            Toast.makeText(context, "try Again", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .setNegativeButton("Cancel") { _, _ -> }
                    .show()
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
        binding.colorPicker.setOnClickListener {
            var defaultColor = Color.RED
            if (viewModel.stopWatch.value?.backgroundColor != null) {
                val defaultColorHex = viewModel.stopWatch.value!!.backgroundColor!!
                val defaultColorColorDrawable = hex2background(defaultColorHex) as ColorDrawable
                defaultColor = defaultColorColorDrawable.color
            }
            ColorPickerPopup.Builder(context)
                .initialColor(defaultColor)
                .enableBrightness(true)
                .enableAlpha(false)
                .okTitle("Ok")
                .cancelTitle("Cancel")
                .showIndicator(true)
                .showValue(false)
                .build()
                .show(
                    parentFragment?.view,
                    object: ColorPickerPopup.ColorPickerObserver() {
                        override fun onColorPicked(color: Int) {
                            val hex = backgroundColor2hex(ColorDrawable(color))
                            binding.stopwatch!!.backgroundColor = hex
                            Log.d("zaq1", "hex: $hex")
                            viewModel.updateStopWatch(binding.stopwatch!!)
                            changeColorOfBg()
                        }
                    }
                )
        }
        binding.colorPhoto.setOnClickListener {
            val actions = arrayOf("Camera", "Gallery")
            AlertDialog.Builder(context)
                .setTitle("Select source of image")
                .setItems(actions) {_, which ->
                    when(which) {
                        0 -> getImageFromCamera()
                        1 -> getImageFromGallery()
                    }
                }
                .show()
        }
    }

    private fun getImageFromCamera() {
        Dexter.withContext(context).withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    Log.d("zaq1", "p0: $p0")
                    Log.d("zaq1", "Cam Start")
                    val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    Log.d("zaq1", "Cam Mid")
                    parentFragment!!.startActivityForResult(intent, REQUEST_CAMERA)
                    Log.d("zaq1", "Cam End")
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(
                        context,
                        "Permission denied",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    showRational()
                }

            }).onSameThread().check()
    }

    private fun getImageFromGallery() {
        Dexter.withContext(context).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object: PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    Log.d("zaq1", "p0: $p0")
                    Log.d("zaq1", "Gal Start")
                    val intent: Intent = Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    Log.d("zaq1", "Gal Mid")
                    parentFragment!!.startActivityForResult(intent, REQUEST_GALLERY)
                    Log.d("zaq1", "Gal End")
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(
                            context,
                            "Permission denied",
                            Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
                    showRational()
                }

            }).onSameThread().check()
    }

    private fun showRational() {
        AlertDialog.Builder(context)
            .setTitle("Permissions required")
            .setMessage("Permission was already denied. Go to settings and turn it on manually.")
            .setPositiveButton("Go to settings") { _, _ ->
                try {
                    val intent: Intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri: Uri =
                            Uri.fromParts("package", context?.packageName, null)
                    intent.data = uri
                    parentFragment?.startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { _, _ ->

            }
            .show()
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
        if (binding.stopwatch != null) {
            // TODO: 2/3/21 sprawdzić czy jak zamienie na "when" to dalej bedzie zachowana funkcjonalnosc else if
            var bg = ColorDrawable(Color.TRANSPARENT)

            if (binding.stopwatch!!.backgroundUrl != null) {
                bg = ColorDrawable(Color.TRANSPARENT)
            } else if (binding.stopwatch!!.backgroundColor != null) {
                bg = hex2background(binding.stopwatch!!.backgroundColor!!) as ColorDrawable
                Log.d("zaq1", "ColorFilter: ${bg.color}")
                val filter = PorterDuffColorFilter(bg.color, PorterDuff.Mode.MULTIPLY)
                binding.colorPicker.colorFilter = filter
            }
            binding.constraintLayoutMain.background = bg
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("zaq1", "onActivityResult")
        Log.d("zaq1", "requestCode: $requestCode")
        Log.d("zaq1", "resultCode: $resultCode")
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CAMERA -> {
                    Log.d("zaq1", "REQUEST_CAMERA ->")
                    if (binding.stopwatch != null) {
                        val image: Bitmap = data?.extras?.get("data") as Bitmap
                        val imagePath = saveImage(image)
                        binding.stopwatch!!.backgroundUrl = imagePath
                        viewModel.updateStopWatch(binding.stopwatch!!)
                        adapter.notifyItemChanged(binding.stopwatch!!.position!!)
                    }
                }
                REQUEST_GALLERY -> {

                }
            }
        }
    }

    private fun saveImage(image: Bitmap): String {
        val wrapper: ContextWrapper = ContextWrapper(context)
        var file: File = wrapper.getDir("myGallery", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")
        try {
            val stream: OutputStream = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Uri.parse(file.absolutePath).toString()
    }

    override fun onResume() {
        super.onResume()
        showCorrectUi()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        handling = false
        handlingStarted = false
    }

}