package com.example.zad_09_03_galleryapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.zad_09_03_galleryapp.databinding.ActivityMainBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import java.io.IOException
import java.security.acl.AclNotFoundException

class MainActivity : AppCompatActivity() {

    companion object {
        private val CAMERA_PERMISSION_CODE = 0
        private val CAMERA_INTENT = 1
        private val GALERY_INTENT = 3
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnAddPicture.setOnClickListener {
            val pictureDialog: AlertDialog.Builder = AlertDialog.Builder(this)
            val dialogItems = arrayOf("Select form gallery", "Capture picture")
            pictureDialog.setTitle("Please select action")
                .setItems(dialogItems) { _, which ->
                    when(which) {
                        0 -> {
                            Log.d("zaq1", "choosePhoto")
                            choosePhoto()
                        }
                        1 -> {
                            Log.d("zaq1", "chooseCamera")
                            chooseCamera()
                        }
                    }
                }
                .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            when(requestCode) {
                CAMERA_INTENT -> {
                    val thumbnail: Bitmap = data.extras?.get("data") as Bitmap
                    binding.imageViewPicture.setImageBitmap(thumbnail)
                }
                GALERY_INTENT -> {
                    val contentUri: Uri? = data.data
                    try {
                        if (Build.VERSION.SDK_INT >= 29)  { // for newer versions
                            val selectedPicture: ImageDecoder.Source =
                                    ImageDecoder.createSource(this.contentResolver, contentUri!!)
                            binding.imageViewPicture.setImageBitmap(
                                    ImageDecoder.decodeBitmap(selectedPicture)
                            )
                        } else { // for older versions
                            val selectedPicture: Bitmap =
                                MediaStore.Images.Media.getBitmap(contentResolver, contentUri)
                            binding.imageViewPicture.setImageBitmap(selectedPicture)
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun chooseCamera() {
        Dexter.withContext(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object: PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, CAMERA_INTENT)
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    TODO("Not yet implemented")
                }

                override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
                    showRationalDialog()
                }
            }).onSameThread().check()
    }

    private fun choosePhoto() {
        Dexter.withContext(this).withPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object: PermissionListener {
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                val intent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(intent, GALERY_INTENT)
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                TODO("Not yet implemented")
            }

            override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
                showRationalDialog()
            }
        }).check()
    }

    private fun showRationalDialog() {
        AlertDialog.Builder(this)
                .setMessage("This feature requires permissions")
                .setPositiveButton("Ask me") { _, _ ->
                    try {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    } catch (e: AclNotFoundException) {
                       e.printStackTrace()
                    }
                }
                .setNegativeButton("CANCEL") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
    }
}