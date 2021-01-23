package com.example.zad_09_03_galleryapp.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zad_09_03_galleryapp.R
import com.example.zad_09_03_galleryapp.models.SingleItemModel

class GalleryAdapter(
    private val context: Context,
    private val list: ArrayList<SingleItemModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(
            R.layout.item_view,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        if (holder is MyViewHolder) {
            val imageViewRecyclerView =
                holder.itemView.findViewById(R.id.image_view_recycler_view) as ImageView
            val textViewTitle =
                holder.itemView.findViewById(R.id.text_view_title) as TextView

            imageViewRecyclerView.setImageURI(Uri.parse(item.image))
            textViewTitle.text = item.title
        }
    }

    override fun getItemCount(): Int = this.list.size

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

}