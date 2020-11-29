package com.example.zad_05_02_pageview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DetailAdapter(
    private val newsList: List<News>,
    private val activity: DetailActivity
) : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val info: TextView = itemView.findViewById(R.id.detail_text_view)
        fun bind(news: News) {
            info.text = news.info
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.detail_pager_item_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNews: News = newsList[position]
        holder.bind(currentNews)
    }

    override fun getItemCount(): Int = newsList.size
}