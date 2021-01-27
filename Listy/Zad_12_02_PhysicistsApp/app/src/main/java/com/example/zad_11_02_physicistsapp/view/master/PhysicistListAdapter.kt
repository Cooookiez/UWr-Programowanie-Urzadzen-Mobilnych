package com.example.zad_11_02_physicistsapp.view.master

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.zad_11_02_physicistsapp.R
import com.example.zad_11_02_physicistsapp.databinding.RecyclerViewItemBinding
import com.example.zad_11_02_physicistsapp.model.Physicist
import com.example.zad_11_02_physicistsapp.util.getProgressDrawable
import com.example.zad_11_02_physicistsapp.util.loadImage

class PhysicistListAdapter(
    private val physicistList: ArrayList<Physicist>
) : RecyclerView.Adapter<PhysicistListAdapter.PhysicistViewHolder>(),
    PhysicistClickListener {

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
        holder.binding.physicist = physicistList[position]
        holder.binding.listener = this
    }

    override fun getItemCount(): Int = physicistList.size

    override fun onPhysicistClicked(view: View) {
        val uuidString = (view.findViewById<View>(R.id.id) as TextView).text.toString()
        val action = ListFragmentDirections.actionDetailFragment(uuidString.toLong())
        Navigation.findNavController(view).navigate(action)
    }

}