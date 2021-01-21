package com.example.zad_09_01_askpremissionfromfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.zad_09_01_askpremissionfromfragment.databinding.FragmentPermissionBinding

class PermissionFragment : Fragment() {

    private lateinit var binding: FragmentPermissionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPermissionBinding.inflate(inflater, container, false)
        val view: View = binding.root

        binding.btnAskForPermission.setOnClickListener {
//            Toast.makeText(context, "onClick", Toast.LENGTH_SHORT).show()
            (activity as MainActivity).checkSmsPermission()
        }

        return view
//        return inflater.inflate(R.layout.fragment_permission, container, false)
    }

    fun onPermissionResult(permissionGranted: Boolean) {

    }
}