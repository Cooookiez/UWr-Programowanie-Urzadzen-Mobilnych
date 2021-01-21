package com.example.zad_09_03_galleryapp

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.zad_09_03_galleryapp.databinding.ActivityMainBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.security.acl.AclNotFoundException

class MainActivity : AppCompatActivity() {

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
                            Log.d("zaq1", "0 -> {")
                            choosePhoto()
                        }
                        1 -> {
                            Toast.makeText(
                                    this,
                                    "Capture picture selected",
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                .show()
        }
    }

    private fun choosePhoto() {
        Dexter.withContext(this).withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).withListener(object: MultiplePermissionsListener{

            override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport?) {
                Log.d("zaq1", "onPermissionsChecked")
                if (multiplePermissionsReport != null && multiplePermissionsReport.areAllPermissionsGranted()) {
                   Log.d("zaq1", "dd")
                }
            }

            override fun onPermissionRationaleShouldBeShown(list: MutableList<PermissionRequest>?, p1: PermissionToken?) {
                Log.d("zaq1", "pre showRationalDialog")
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