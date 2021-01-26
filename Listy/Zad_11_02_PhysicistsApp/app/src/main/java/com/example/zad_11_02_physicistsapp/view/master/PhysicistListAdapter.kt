package com.example.zad_11_02_physicistsapp.view.master

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zad_11_02_physicistsapp.R
import com.example.zad_11_02_physicistsapp.databinding.RecyclerViewItemBinding
import com.example.zad_11_02_physicistsapp.model.Physicist

class PhysicistListAdapter(
    private val physicistList: ArrayList<Physicist>
) : RecyclerView.Adapter<PhysicistListAdapter.PhysicistViewHolder>() {

    inner class PhysicistViewHolder(var binding: RecyclerViewItemBinding)
        : RecyclerView.ViewHolder(binding.root) {}

    fun updateList(newList: List<Physicist>) {
        physicistList.clear()
        physicistList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhysicistListAdapter.PhysicistViewHolder {
        return PhysicistViewHolder(
            RecyclerViewItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: PhysicistListAdapter.PhysicistViewHolder, position: Int) {
        val current: Physicist = physicistList[position]

        holder.binding.rvName.text = current.name
        holder.binding.rvLife.text = current.life
        holder.binding.rvNationality.text = current.nationality
    }

    override fun getItemCount(): Int = physicistList.size

}