package com.example.studentcrimeapp

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.studentcrimeapp.databinding.FragmentCrimePagerBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class CrimeFragment : Fragment {

    private lateinit var crimeViewPager: ViewPager2
    private lateinit var crime: Crime
    private lateinit var btnGoToFirst: Button
    private lateinit var btnGoToLast: Button

    private var adapter: CrimePagerAdapter? = null
    private var mContext: Context
    private lateinit var binding: FragmentCrimePagerBinding

    companion object {
        var quit: Int = 0

        private val DATE_REQUEST: Int = 0
        private val TIME_REQUESTG: Int = 1
        private val CAMERA_REQUEST: Int = 2
        private val GALLERY_REQUEST: Int = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle: Bundle? = arguments
        this.crime = if (bundle != null) {
            CrimeLab[this.mContext]!!
                .getCrimeById(UUID.fromString(bundle.getString("id")))
        } else {
            Crime()
        }
    }

    constructor(context: Context) {
        this.mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentCrimePagerBinding.inflate(layoutInflater)
//        val view: View = inflater
//            .inflate(R.layout.fragment_crime_pager, container, false)
        val view: View = this.binding.root
        this.crimeViewPager = view.findViewById(R.id.viewCrimePager2)
        this.btnGoToFirst = view.findViewById(R.id.btnGoToFirst)
        this.btnGoToLast = view.findViewById(R.id.btnGoToLast)

        if (this.adapter == null) {
            this.adapter = CrimePagerAdapter(inflater.context, this)
            this.crimeViewPager.adapter = this.adapter
            this.crimeViewPager.currentItem =
                CrimeLab[inflater.context]!!.getIndexById(this.crime.getId())
        }

        this.btnGoToFirst.setOnClickListener {
            this.crimeViewPager.setCurrentItem(0, true)
        }

        this.btnGoToLast.setOnClickListener {
            val index = CrimeLab[view.context]?.getCrimes()!!.size
            this.crimeViewPager.setCurrentItem(index - 1, true)
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        quit = crimeViewPager.currentItem
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            this.crime = CrimeLab[this.mContext]!!.getCrimes()[this.crimeViewPager.currentItem]
            when (requestCode) {
                DATE_REQUEST -> {
                    val date: Date = data!!
                        .getSerializableExtra(DatePickerFragment.DATE_EXTRA) as Date

                    val gCalender: Date = GregorianCalendar(
                        date.year + 1900,
                        date.month,
                        date.date,  // nie wiem czemu dziala .date, ale nie
                        this.crime.getDate().hours,
                        this.crime.getDate().minutes
                    ).time as Date

                    CrimeLab[this.mContext]!!.getCrimes()[this.crimeViewPager.currentItem]
                        .setDate(gCalender)
                    CrimeLab[this.mContext]!!.updateAtIndexOf(this.crimeViewPager.currentItem)
                    this.adapter!!.notifyItemChanged(this.crimeViewPager.currentItem)
                }
                TIME_REQUESTG -> {
                    val date: Date = data!!
                        .getSerializableExtra(TimePickerFragment.TIME_EXTRA) as Date

                    val gCalendar: Date = GregorianCalendar(
                        this.crime.getDate().year + 1900,
                        this.crime.getDate().month,
                        this.crime.getDate().date,
                        date.hours,
                        date.minutes
                    ).time as Date

                    CrimeLab[this.mContext]!!.getCrimes()[this.crimeViewPager.currentItem]
                        .setDate(gCalendar)
                    CrimeLab[this.mContext]!!.updateAtIndexOf(this.crimeViewPager.currentItem)
                    this.adapter!!.notifyItemChanged(this.crimeViewPager.currentItem)
                }
                CAMERA_REQUEST -> {
                    val image: Bitmap = data?.extras?.get("data") as Bitmap
                    val imagePath = saveImage(image)
                    CrimeLab[mContext]?.getCrimes()?.get(this.binding.viewCrimePager2.currentItem)
                        ?.setImagePath(imagePath.toString())
                    CrimeLab[mContext]!!.updateAtIndexOf(this.binding.viewCrimePager2.currentItem)
                    this.adapter!!.notifyItemChanged(this.crimeViewPager.currentItem)
                }
                GALLERY_REQUEST -> {
                    val contentUri: Uri = data?.data as Uri
                    try {
                        val selectedImage: ImageDecoder.Source =
                            ImageDecoder.createSource(context?.contentResolver!!, contentUri)
                        val imagePath = saveImage(ImageDecoder.decodeBitmap(selectedImage))
                        CrimeLab[mContext]?.getCrimes()?.get(this.binding.viewCrimePager2.currentItem)
                            ?.setImagePath(imagePath.toString())
                        CrimeLab[mContext]!!.updateAtIndexOf(this.binding.viewCrimePager2.currentItem)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    this.adapter!!.notifyItemChanged(this.crimeViewPager.currentItem)
                }
            }
        } else {
            return
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

    override fun onDetach() {
        super.onDetach()
        quit = crimeViewPager.currentItem
    }

}
