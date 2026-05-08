package com.modela.app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.modela.app.data.model.JobOpening
import com.modela.app.databinding.ItemJobOpeningBinding
import com.modela.app.util.CompanyVisualHelper
import com.modela.app.util.JobTagHelper
import com.modela.app.util.loadImage

class JobOpeningAdapter(
    private val onClick: (JobOpening) -> Unit
) : ListAdapter<JobOpening, JobOpeningAdapter.ViewHolder>(DIFF) {

    inner class ViewHolder(private val binding: ItemJobOpeningBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(job: JobOpening) {
            binding.ivJobCampaign.loadImage(job.campaignImageUrl)
            binding.ivJobCompanyLogo.setImageResource(CompanyVisualHelper.logoForJob(job.id))
            binding.tvJobCompanyName.text = job.companyName
            binding.tvJobCompanyLocation.text = job.companyLocation
            binding.tvJobPostedTime.text = JobTagHelper.formatPostedTime(job.postedAt)
            binding.tvJobTitle.text = job.jobTitle
            binding.tvJobDescription.text = job.jobDescription
            binding.tvJobBudget.text = job.budget
            binding.tvJobApplicants.text = "${job.applicants} cand. - ${job.location}"

            JobTagHelper.addTagsTo(binding.tagContainer, job.tags)

            binding.cardJobOpening.setOnClickListener { onClick(job) }
            binding.btnJobApply.setOnClickListener { onClick(job) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemJobOpeningBinding.inflate(
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
