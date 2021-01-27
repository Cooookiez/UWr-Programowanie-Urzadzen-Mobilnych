package com.example.zad_11_02_physicistsapp.view.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.zad_11_02_physicistsapp.R
import com.example.zad_11_02_physicistsapp.databinding.FragmentDetailBinding
import com.example.zad_11_02_physicistsapp.databinding.FragmentListBinding
import com.example.zad_11_02_physicistsapp.util.getProgressDrawable
import com.example.zad_11_02_physicistsapp.util.loadImage
import com.example.zad_11_02_physicistsapp.viewmodel.DetailViewModel


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private var physicistUuid = 0L

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments.let {
            physicistUuid = DetailFragmentArgs.fromBundle(it!!).physicistUuid
        }
        viewModel.fetch(physicistUuid)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.physicist.observe(viewLifecycleOwner, { physicist ->
            physicist?.let {
                binding.physicist = physicist
//                binding.dvName.text = physicist.name
//                binding.dvLife.text = physicist.life
//                binding.dvNationality.text = physicist.nationality
//                binding.dvMotivation.text = physicist.motivation
//                context?.let {
//                    binding.dvPicture.loadImage(
//                        physicist.imageUrl,
//                        getProgressDrawable(it)
//                    )
//                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}