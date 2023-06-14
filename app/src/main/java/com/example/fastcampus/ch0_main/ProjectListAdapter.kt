package com.example.fastcampus.ch0_main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.fastcampus.databinding.ItemProjectBinding

class ProjectListAdapter(
    private val list: List<Project>
) : RecyclerView.Adapter<ProjectListAdapter.ProjectListViewHolder>() {

    var projectListClickListener: ((Project) -> Unit)? = null

    class ProjectListViewHolder(val binding: ItemProjectBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProjectListViewHolder {
        val binding =
            ItemProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectListViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ProjectListViewHolder, position: Int) {

        val item = list[position]

        with(holder.binding) {
            val str = (position + 1).toString() + ". " + item.name
            projectNameTextView.text = str

            holder.binding.root.setOnClickListener {
                projectListClickListener?.invoke(item)
            }
        }
    }


}