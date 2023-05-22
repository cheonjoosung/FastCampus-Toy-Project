package com.example.fastcampus.ch06_word_book

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fastcampus.databinding.ItemWordBinding

class WordAdapter(
    val list: MutableList<Word>
) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    class WordViewHolder(val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    var click: ((Word) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemWordBinding.inflate(inflater, parent, false)
        return WordViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {

        val item = list[position]
        holder.binding.apply {

            textTextView.text = item.text
            meanTextView.text = item.mean
            typeChip.text = item.type


        }

        holder.itemView.setOnClickListener {
            click?.invoke(item)
        }
    }
}