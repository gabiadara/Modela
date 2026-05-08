package com.modela.app.ui.proposals

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.modela.app.R
import com.modela.app.data.model.CompanyCampaign
import com.modela.app.databinding.ItemCompanyCampaignBinding
import com.modela.app.util.loadImage

class CompanyCampaignAdapter(
    private val onClick: (CompanyCampaign) -> Unit = {}
) :
    ListAdapter<CompanyCampaign, CompanyCampaignAdapter.ViewHolder>(DIFF) {

    inner class ViewHolder(private val binding: ItemCompanyCampaignBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(campaign: CompanyCampaign) {
            binding.ivCampaignCover.loadImage(campaign.coverImageUrl)
            binding.tvCampaignType.text = campaign.type
            binding.tvCampaignTitle.text = campaign.title
            binding.tvCampaignStatus.text = campaign.status
            binding.tvCampaignDescription.text = campaign.description
            binding.tvCampaignMeta.text = "${campaign.applicants} perfis - ${campaign.location}"
            binding.tvCampaignBudget.text = campaign.budget

            binding.campaignTagContainer.removeAllViews()
            campaign.tags.forEach { tag ->
                binding.campaignTagContainer.addView(buildTag(tag))
            }
            binding.root.setOnClickListener { onClick(campaign) }
        }

        private fun buildTag(tag: String): TextView {
            val density = binding.root.resources.displayMetrics.density
            return TextView(binding.root.context).apply {
                text = tag
                textSize = 11f
                setTextColor(Color.parseColor("#5E5E5E"))
                setPadding((12 * density).toInt(), 0, (12 * density).toInt(), 0)
                gravity = android.view.Gravity.CENTER
                background = GradientDrawable().apply {
                    shape = GradientDrawable.RECTANGLE
                    cornerRadius = 50 * density
                    setColor(Color.parseColor("#FFF7F7F7"))
                    setStroke(1, Color.parseColor("#14000000"))
                }
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    (28 * density).toInt()
                ).apply {
                    marginEnd = binding.root.resources.getDimensionPixelSize(R.dimen.spacing_sm)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCompanyCampaignBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<CompanyCampaign>() {
            override fun areItemsTheSame(a: CompanyCampaign, b: CompanyCampaign) = a.id == b.id
            override fun areContentsTheSame(a: CompanyCampaign, b: CompanyCampaign) = a == b
        }
    }
}
