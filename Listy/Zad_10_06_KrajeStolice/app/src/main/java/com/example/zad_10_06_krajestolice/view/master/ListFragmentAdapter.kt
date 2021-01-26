package com.example.zad_10_06_krajestolice.view.master

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zad_10_06_krajestolice.databinding.FragmentListItemRvBinding
import com.example.zad_10_06_krajestolice.model.Country
import com.example.zad_10_06_krajestolice.util.getProgressDrawable
import com.example.zad_10_06_krajestolice.util.loadImage

class ListFragmentAdapter(
    private val countriesList: ArrayList<Country>
) : RecyclerView.Adapter<ListFragmentAdapter.CountryViewHolder>() {

    inner class CountryViewHolder(var binding: FragmentListItemRvBinding)
        : RecyclerView.ViewHolder(binding.root)

    fun updateList(newList: List<Country>) {
        countriesList.clear()
        countriesList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListFragmentAdapter.CountryViewHolder {
        return CountryViewHolder(
            FragmentListItemRvBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val current: Country = countriesList[position]

        holder.binding.rvTextViewName.text = current.name
        holder.binding.rvTextViewCapital.text = current.name
        holder.binding.rvImageViewFlag.loadImage(
            current.flag_url,
            getProgressDrawable(holder.binding.rvImageViewFlag.context)
        )
    }

    override fun getItemCount(): Int = countriesList.size

}