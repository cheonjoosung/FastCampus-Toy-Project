package com.example.fastcampus.ch0_main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.fastcampus.databinding.ItemProjectBinding

class ProjectListAdapter(
    val onClick: (Project) -> Unit
) : ListAdapter<Project, ProjectListAdapter.ProjectListViewHolder>(diffUtil) {

    inner class ProjectListViewHolder(val binding: ItemProjectBinding) : ViewHolder(binding.root) {
        fun bind(item: Project) {

            with(binding) {
                val str = (layoutPosition + 1).toString() + ". " + item.name
                projectNameTextView.text = str

                root.setOnClickListener {
                    onClick.invoke(item)
                }

                item.imageResInt?.let {
                    projectImageView.setImageResource(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProjectListViewHolder {
        val binding =
            ItemProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectListViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Project>() {
            override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
                return oldItem.claasName == newItem.claasName
            }

            override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
                return oldItem == newItem
            }

        }
    }


}