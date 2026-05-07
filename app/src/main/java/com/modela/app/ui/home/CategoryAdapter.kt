package com.modela.app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.modela.app.R
import com.modela.app.data.model.Category
import com.modela.app.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val onClick: (Category) -> Unit
) : ListAdapter<Category, CategoryAdapter.ViewHolder>(DiffCallback()) {

    private var selectedPosition = -1

    inner class ViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category, position: Int) {
            binding.tvCategory.text = category.name
            binding.tvCategory.isSelected = position == selectedPosition
            binding.tvCategory.setTextColor(
                ContextCompat.getColor(binding.root.context,
                    if (position == selectedPosition) R.color.match_obsidian else R.color.match_text_primary)
            )
            binding.root.setOnClickListener {
                val prev = selectedPosition
                selectedPosition = if (selectedPosition == position) -1 else position
                notifyItemChanged(prev)
                notifyItemChanged(position)
                onClick(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position), position)

    class DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(a: Category, b: Category) = a.id == b.id
        override fun areContentsTheSame(a: Category, b: Category) = a == b
    }
}
