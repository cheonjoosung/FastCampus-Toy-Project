package com.example.fastcampus.part1.ch07_my_gallery

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fastcampus.databinding.ItemFrameBinding

class FrameAdapter(
    private val list: List<FrameItem>
) : RecyclerView.Adapter<FrameAdapter.FrameViewHolder>() {

    class FrameViewHolder(val binding: ItemFrameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FrameItem) {
            binding.frameImageView.setImageURI(item.uri)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrameViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemFrameBinding.inflate(inflater, parent, false)
        return FrameViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: FrameViewHolder, position: Int) {
        holder.bind(list[position])
    }
}

data class FrameItem(
    val uri: Uri
)