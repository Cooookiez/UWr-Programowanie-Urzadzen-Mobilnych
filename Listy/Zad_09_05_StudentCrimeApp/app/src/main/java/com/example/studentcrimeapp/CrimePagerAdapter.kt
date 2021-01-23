package com.example.studentcrimeapp

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentcrimeapp.databinding.FragmentCrimeBinding
import com.example.studentcrimeapp.databinding.FragmentCrimePagerBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.util.*

class CrimePagerAdapter : RecyclerView.Adapter<CrimePagerAdapter.ViewHolder> {

    companion object {
        private val DATE_DIALOG: String = "DATE"
        private val DATE_REQUEST: Int = 0
        private val TIME_DIALOG: String = "TIME"
        private val TIME_REQUEST: Int = 1
        private val CAMERA_REQUEST: Int = 2
        private val GALLERY_REQUEST: Int = 3
    }

    private var mContext: Context
    private var mParent: CrimeFragment

    lateinit var binding: FragmentCrimeBinding

    constructor(context: Context, parent: CrimeFragment) {
        this.mContext = context
        this.mParent = parent
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.fragment_crime, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        this.binding = FragmentCrimeBinding.inflate(LayoutInflater.from(mContext))

        val title: String = CrimeLab[mContext]!!.getCrimes()[holder.adapterPosition].getTitle()
        val date: Date = CrimeLab[mContext]!!.getCrimes()[holder.adapterPosition].getDate()
        val solved: Boolean = CrimeLab[mContext]!!.getCrimes()[holder.adapterPosition].getSolved()
        val imagePath: String = CrimeLab[mContext]!!.getCrimes()[holder.adapterPosition].getImagePath()


        // Title change and save
        holder.crime_title.setText(title)           // ni mam pojecia czemu tu nie dziala ".text = "
        holder.crime_title.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                CrimeLab[mContext]!!.getCrimes()[holder.adapterPosition].setTitle(s.toString())
                CrimeLab[mContext]!!.updateAtIndexOf(holder.adapterPosition)
            }

        })

        // Date change and save
        holder.crime_date.text = date.toString()
        holder.crime_date.setOnClickListener {
            val fragmentManager: FragmentManager? = this.mParent.fragmentManager
            val dialog: DatePickerFragment =
                DatePickerFragment.newInstance( CrimeLab[this.mContext]!!
                    .getCrimes()[holder.adapterPosition].getDate() )
            dialog.setTargetFragment(this.mParent, DATE_REQUEST)
            dialog.show(fragmentManager!!, DATE_DIALOG)
        }

        // Time change and save
        val hours = if (date.hours < 10) {
            "0${date.hours}"
        } else {
            "${date.hours}"
        }
        val minutes = if (date.minutes < 10) {
            "0${date.minutes}"
        } else {
            "${date.minutes}"
        }
        holder.crime_time.text = "$hours:$minutes"
        holder.crime_time.setOnClickListener {
            val fragmentManager: FragmentManager? = this.mParent.fragmentManager
            val dialog: TimePickerFragment =
                TimePickerFragment.newInstance( CrimeLab[this.mContext]!!
                    .getCrimes()[holder.adapterPosition].getDate() )
            dialog.setTargetFragment(this.mParent, TIME_REQUEST)
            dialog.show(fragmentManager!!, TIME_DIALOG)
        }

        // Crime solved status
        holder.crime_solved.isChecked = solved
        holder.crime_solved.setOnCheckedChangeListener { _, isChecked ->
            CrimeLab[mContext]!!.getCrimes()[holder.adapterPosition].setSolvedTo(isChecked)
            CrimeLab[mContext]!!.updateAtIndexOf(holder.adapterPosition)
        }

        // image add / change
        Log.d("zaq1 – imagePath", "imagePath = '$imagePath'")
        if (imagePath.isNotEmpty() && imagePath != "null") {
            Log.d("zaq1 – imagePath", "inside")
            holder.image_view_photo.setImageURI(Uri.parse(imagePath))
        }
        holder.btnAddPhoto.setOnClickListener {
            val actions = arrayOf("Camera", "Gallery")
            AlertDialog.Builder(mContext)
                .setTitle("Select sorce of new photo")
                .setItems(actions) { _, which ->
                    when(which) {
                        0 -> chooseCamera()
                        1 -> chooseGallery()
                    }
                }
                .show()
            holder.image_view_photo.setImageURI(Uri.parse(imagePath))
        }

        // Delete crime
        holder.btnDelete.setOnClickListener {
            CrimeLab[mContext]!!.removeAtIndexOf(holder.adapterPosition)
            notifyDataSetChanged()
        }

    }

    private fun chooseCamera() {
        Dexter.withContext(mContext).withPermission(
            Manifest.permission.CAMERA
        ).withListener(object: PermissionListener{
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                mParent.startActivityForResult(intent, CAMERA_REQUEST)
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) { }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?,
                p1: PermissionToken?
            ) {
                showRationalDialog()
            }

        }).onSameThread().check()
    }

    private fun chooseGallery() {
        Dexter.withContext(mContext).withPermission(
            Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object: PermissionListener{
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                val intent: Intent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                mParent.startActivityForResult(intent, GALLERY_REQUEST)
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) { }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?,
                p1: PermissionToken?
            ) {
                showRationalDialog()
            }

        }).onSameThread().check()
    }

    private fun showRationalDialog() {
        AlertDialog.Builder(mContext)
            .setMessage("Permissions required")
            .setPositiveButton("Ok") { _, _ ->
                try {
                    val intent: Intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri: Uri =
                        Uri.fromParts("package", mContext.packageName, null)
                    intent.setData(uri)
                    mParent.startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Deny") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun getItemCount(): Int { return CrimeLab[mContext]!!.getCrimes().size }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var crime_title: EditText
        var crime_date: Button
        var crime_time: Button
        var crime_solved: CheckBox
        var btnDelete: Button
        var image_view_photo: ImageView
        var btnAddPhoto: ImageButton

        init {
            crime_title = itemView.findViewById(R.id.crime_title)
            crime_date = itemView.findViewById(R.id.crime_date)
            crime_time = itemView.findViewById(R.id.crime_time)
            crime_solved = itemView.findViewById(R.id.crime_solved)
            btnDelete = itemView.findViewById(R.id.btnDelete)
            image_view_photo = itemView.findViewById(R.id.image_view_photo)
            btnAddPhoto = itemView.findViewById(R.id.btnAddPhoto)
        }
    }

}