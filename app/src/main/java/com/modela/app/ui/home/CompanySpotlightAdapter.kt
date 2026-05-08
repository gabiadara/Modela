package com.modela.app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.modela.app.data.model.JobOpening
import com.modela.app.databinding.ItemCompanySpotlightBinding
import com.modela.app.util.CompanyVisualHelper
import com.modela.app.util.loadImage

class CompanySpotlightAdapter(
    private val onClick: (JobOpening) -> Unit
) : ListAdapter<JobOpening, CompanySpotlightAdapter.ViewHolder>(DIFF) {

    inner class ViewHolder(private val binding: ItemCompanySpotlightBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(job: JobOpening) {
            binding.ivSpotlightCampaign.loadImage(job.campaignImageUrl)
            binding.ivSpotlightLogo.setImageResource(CompanyVisualHelper.logoForJob(job.id))
            binding.tvSpotlightCompanyName.text = job.companyName
            binding.tvSpotlightTitle.text = job.jobTitle
            binding.tvSpotlightBudget.text = job.budget
            binding.tvSpotlightCategory.text = job.tags.firstOrNull()?.label ?: job.category
            binding.cardCompanySpotlight.setOnClickListener { onClick(job) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCompanySpotlightBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<JobOpening>() {
            override fun areItemsTheSame(a: JobOpening, b: JobOpening) = a.id == b.id
            override fun areContentsTheSame(a: JobOpening, b: JobOpening) = a == b
        }
    }
}
