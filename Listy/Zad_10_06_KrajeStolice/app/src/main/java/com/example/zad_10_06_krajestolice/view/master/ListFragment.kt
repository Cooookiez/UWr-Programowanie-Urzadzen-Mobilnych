package com.example.zad_10_06_krajestolice.view.master

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zad_10_06_krajestolice.R
import com.example.zad_10_06_krajestolice.databinding.FragmentListBinding
import com.example.zad_10_06_krajestolice.viewmodel.ListViewModel

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListViewModel by viewModels()
    private val adapter: ListFragmentAdapter = ListFragmentAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.refresh()
        binding.recyclerViewList.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = this@ListFragment.adapter

            binding.swipeToRefresh.setOnRefreshListener {
                binding.loadingProgressbar.visibility = View.VISIBLE
                binding.textViewError.visibility = View.GONE
                binding.recyclerViewList.visibility = View.GONE
                viewModel.refreshFromRemote()
                binding.swipeToRefresh.isRefreshing = false
            }

            observerViewModel()
        }
    }

    private fun observerViewModel() {
        viewModel.country.observe(viewLifecycleOwner, { country ->
            country.let {
                binding.recyclerViewList.visibility = View.VISIBLE
                adapter.updateList(country)
            }
        })
        viewModel.countryLoadError.observe(viewLifecycleOwner, { isError ->
            isError?.let {
                binding.textViewError.visibility = if(isError) View.VISIBLE else View.GONE
            }
        })
        viewModel.countryLoading.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let {
                if (isLoading) {
                    binding.loadingProgressbar.visibility = View.VISIBLE
                    binding.textViewError.visibility = View.GONE
                    binding.recyclerViewList.visibility = View.GONE
                } else {
                    binding.loadingProgressbar.visibility = View.GONE
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}