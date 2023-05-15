package com.example.fastcampus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.fastcampus.databinding.ItemProjectListBinding

class ProjectListAdapter(
    private val list: List<ProjectList>
) : RecyclerView.Adapter<ProjectListAdapter.ProjectListViewHolder>() {

    var projectListClickListener: ((ProjectList) -> Unit)? = null

    class ProjectListViewHolder(val binding: ItemProjectListBinding) : ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProjectListViewHolder {
        val binding =
            ItemProjectListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectListViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ProjectListViewHolder, position: Int) {

        val item = list[position]

        with(holder.binding) {
            val str = (position + 1).toString() + ". " + item.name
            btnName.text = str

            btnName.setOnClickListener {
                projectListClickListener?.invoke(item)
            }
        }
    }


}