package com.example.studentcrimeapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import java.util.*

class CrimeFragment : Fragment {

    private lateinit var crimeViewPager: ViewPager2
    private lateinit var crime: Crime
    private lateinit var btnGoToFirst: Button
    private lateinit var btnGoToLast: Button

    private var adapter: CrimePagerAdapter? = null
    private var mContext: Context

    companion object {
        var quit: Int = 0

        private val DATE_DIALOG: String = "DATE"
        private val DATE_REQUEST: Int = 0
        private val TIME_DIALOG: String = "TIME"
        private val TIME_REQUESTG: Int = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle: Bundle? = arguments
        if (bundle != null) {
            this.crime = CrimeLab.get(this.mContext)!!.getValueById(UUID.fromString(bundle.getString("id")))
        } else {
            this.crime = Crime()
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
        val view: View = inflater.inflate(R.layout.fragment_crime_pager, container, false)
        this.crimeViewPager = view.findViewById(R.id.viewCrimePager2)
        this.btnGoToFirst = view.findViewById(R.id.btnGoToFirst)
        this.btnGoToLast = view.findViewById(R.id.btnGoToLast)

        if (this.adapter == null) {
            this.adapter = CrimePagerAdapter(inflater.context, this)
            this.crimeViewPager.adapter = this.adapter
            this.crimeViewPager.currentItem = CrimeLab
                    .get(inflater.context)!!.getIndexById(this.crime.getId())
        }

        this.btnGoToFirst.setOnClickListener {
            this.crimeViewPager.setCurrentItem(0, true)
        }

        this.btnGoToLast.setOnClickListener {
            val index = CrimeLab.get(view.context)?.getCrimes()!!.size
            this.crimeViewPager.setCurrentItem(index - 1, true)
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        quit = crimeViewPager.currentItem
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            this.crime = CrimeLab[this.mContext]!!.getCrimes()[this.crimeViewPager.currentItem]
            when (requestCode) {
                DATE_REQUEST -> {
                    val date: Date = data!!
                        .getSerializableExtra(DatePickerFragment.DATE_EXTRA) as Date

                    val gCalender: Date = GregorianCalendar(
                        date.year,
                        date.month,
                        date.day,
                        this.crime.getDate().hours,
                        this.crime.getDate().minutes
                    ) as Date

                    CrimeLab[this.mContext]!!.getCrimes()[this.crimeViewPager.currentItem]
                        .setDate(gCalender)
                    CrimeLab[this.mContext]!!.updateAtIndexOf(this.crimeViewPager.currentItem)
                    this.adapter!!.notifyItemChanged(this.crimeViewPager.currentItem)
                }
                TIME_REQUESTG -> {
                    val date: Date = data!!
                        .getSerializableExtra(TimePickerFragment.TIME_EXTRA) as Date

                    val gCalendar: Date = GregorianCalendar(
                        this.crime.getDate().year,
                        this.crime.getDate().month,
                        this.crime.getDate().day,
                        date.hours,
                        date.minutes
                    ) as Date

                    CrimeLab[this.mContext]!!.getCrimes()[this.crimeViewPager.currentItem]
                        .setDate(gCalendar)
                    CrimeLab[this.mContext]!!.updateAtIndexOf(this.crimeViewPager.currentItem)
                    this.adapter!!.notifyItemChanged(this.crimeViewPager.currentItem)
                }
            }
        } else {
            return
        }
    }

    override fun onDetach() {
        super.onDetach()
        quit = crimeViewPager.currentItem
    }

}
