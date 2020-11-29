package com.example.zad_05_02_pageview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class NewsAdapter(
    private val newsList: List<News>,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder (itemView) {

        private val picture: ImageView = itemView.findViewById(R.id.picture_image_view)

        fun bind(news: News, onClickListener: OnClickListener) {
            picture.setImageResource(news.picture)
            itemView.setOnClickListener{
                onClickListener.onClick(news)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_view, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news: News = newsList[position]
        holder.bind(news, onClickListener)
    }

    override fun getItemCount(): Int = newsList.size

    class OnClickListener(val clickListener: (news: News) -> Unit) {
        fun onClick(news: News) = clickListener(news)
    }

}