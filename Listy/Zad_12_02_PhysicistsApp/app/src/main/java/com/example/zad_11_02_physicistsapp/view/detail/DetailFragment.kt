package com.example.zad_11_02_physicistsapp.view.detail

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.zad_11_02_physicistsapp.R
import com.example.zad_11_02_physicistsapp.databinding.FragmentDetailBinding
import com.example.zad_11_02_physicistsapp.databinding.FragmentListBinding
import com.example.zad_11_02_physicistsapp.model.PhysicistPalette
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
                it.imageUrl.let { url ->
                    setupBackgroundColor(url)
                }
            }
        })
    }

    private fun setupBackgroundColor(url: String?) {
        Glide
            .with(this)
            .asBitmap()
            .load(url)
            .into(object: CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate { palette ->
                            val swatch = palette?.lightVibrantSwatch
                            val backgroundColor: Int = swatch?.rgb?:0 // <- default zero
                            val textColor: Int = swatch?.bodyTextColor?:Color.BLACK
                            val titleColor: Int = swatch?.titleTextColor?:Color.BLACK
                            val physicistPalette = PhysicistPalette(
                                backgroundColor,
                                textColor,
                                titleColor
                            )
                            binding.palette = physicistPalette
                        }
                }

                override fun onLoadCleared(placeholder: Drawable?) { }

            })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}