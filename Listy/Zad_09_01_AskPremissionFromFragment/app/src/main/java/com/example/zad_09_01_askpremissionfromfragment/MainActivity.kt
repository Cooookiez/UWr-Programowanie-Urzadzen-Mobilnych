package com.example.zad_09_01_askpremissionfromfragment

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    companion object {
        private const val PERMISSION_SMS: Int = 0
    }

    private var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fragmentManager: FragmentManager = supportFragmentManager
        fragment = fragmentManager.findFragmentById(R.id.fragment_container)

        if (fragment == null) {
            fragment = PermissionFragment()
            fragmentManager.beginTransaction().add(
                R.id.fragment_container,
                fragment as PermissionFragment
            ).commit()
        }
    }

    fun checkSmsPermission() {
        Log.d("zaq1 – checkSmsPermission", "start")
        val permissionGranted =
            ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
        Log.d("zaq1 – checkSmsPermission", "permissionGranted = $permissionGranted")
        Log.d(
            "zaq1 – checkSmsPermission",
            "PERMISSION_GRANTED = ${PackageManager.PERMISSION_GRANTED}"
        )
        if (permissionGranted != PackageManager.PERMISSION_GRANTED) {
            Log.d("zaq1 – checkSmsPermission", "in if")
            val permissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.SEND_SMS
                )
            Log.d("zaq1 – permissionRationale", "$permissionRationale")
            if (!permissionRationale) {
                Log.d("zaq1 – permissionRationale", "in if 2")
                AlertDialog.Builder(this)
                    .setTitle("Chce wysyłąć eSeMeSy")
                    .setMessage("(Napewno nie po to by wysyłać płatne SMSy)")
                    .setPositiveButton("Łaj not") { _, _ ->
                        Log.d("zaq1 – permissionRationale", "setPositiveButton")
                        requestPermissions()
                    }
                    .setNegativeButton("xD lol, nope") { _, _ ->
                        Log.d("zaq1 – permissionRationale", "setNegativeButton")
                        notifyFragment(false)
                    }
                    .show()
                Log.d("zaq1 – permissionRationale", "out if 2")
            }
        } else {
            notifyFragment(true)
//            this.permissionGranted = true
//            val smsManager: SmsManager = SmsManager.getDefault()
//            smsManager.sendTextMessage(
//                "+48 123 456 789",
//                null,
//                "SMS text",
//                null,
//                null
//            )
            // TODO SMS functionality
        }
    }

    private fun notifyFragment(permissionGranted: Boolean) {
        val activeFragment: Fragment? = this.fragment
            ?.childFragmentManager
            ?.primaryNavigationFragment
        if (activeFragment is PermissionFragment) {
            activeFragment.onPermissionResult(permissionGranted) as PermissionFragment
        }
    }

    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.SEND_SMS
        )
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_SMS)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_SMS) {
            notifyFragment(
                grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED
            )
        }
    }

}