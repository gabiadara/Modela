package com.modela.app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.modela.app.data.model.ModelProfile
import com.modela.app.databinding.ItemFeaturedModelBinding
import com.modela.app.util.loadImage

class FeaturedModelsAdapter(
    private val onClick: (ModelProfile) -> Unit
) : ListAdapter<ModelProfile, FeaturedModelsAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(val binding: ItemFeaturedModelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ModelProfile) {
            binding.tvModelName.text = model.name
            binding.tvModelCategory.text = model.category
            binding.tvRating.text = model.rating.toString()
            binding.ivModelPhoto.loadImage(model.profileImageUrl)
            binding.root.setOnClickListener { onClick(model) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemFeaturedModelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    class DiffCallback : DiffUtil.ItemCallback<ModelProfile>() {
        override fun areItemsTheSame(a: ModelProfile, b: ModelProfile) = a.id == b.id
        override fun areContentsTheSame(a: ModelProfile, b: ModelProfile) = a == b
    }
}
