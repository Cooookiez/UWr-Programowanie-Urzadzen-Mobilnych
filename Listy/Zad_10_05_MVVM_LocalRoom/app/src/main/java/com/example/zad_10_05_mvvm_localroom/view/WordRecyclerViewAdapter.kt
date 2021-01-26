package com.example.zad_10_05_mvvm_localroom.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zad_10_05_mvvm_localroom.R
import com.example.zad_10_05_mvvm_localroom.model.WordEntity

class WordRecyclerViewAdapter
    : ListAdapter<WordEntity, WordRecyclerViewAdapter.WordViewHolder>(WordsComparator()){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WordViewHolder {
        return WordViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_view,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.word)
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.text_view_rv)

        fun bind(text: String?) {
            wordItemView.text = text
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<WordEntity>() {
        override fun areItemsTheSame(oldItem: WordEntity, newItem: WordEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: WordEntity, newItem: WordEntity): Boolean {
            return oldItem.word == newItem.word
        }
    }
}