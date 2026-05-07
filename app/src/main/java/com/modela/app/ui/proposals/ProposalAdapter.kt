package com.modela.app.ui.proposals

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.modela.app.data.model.Proposal
import com.modela.app.data.model.ProposalStatus
import com.modela.app.databinding.ItemProposalBinding
import com.modela.app.util.loadImage

class ProposalAdapter(
    private val isCompany: Boolean,
    private val onAccept: (Proposal) -> Unit,
    private val onReject: (Proposal) -> Unit,
    private val onClick: (Proposal) -> Unit
) : ListAdapter<Proposal, ProposalAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(val binding: ItemProposalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(proposal: Proposal) {
            // Show model info if company, company info if model
            if (isCompany) {
                binding.tvProposalName.text = proposal.modelName
                binding.ivProposalPhoto.loadImage(proposal.modelImageUrl)
            } else {
                binding.tvProposalName.text = proposal.companyName
                binding.ivProposalPhoto.loadImage(proposal.companyImageUrl)
            }

            binding.tvProposalCategory.text = proposal.category
            binding.tvJobTitle.text = proposal.jobTitle
            binding.tvJobDescription.text = proposal.jobDescription
            binding.tvProposalLocation.text = "Local: ${proposal.location}"
            binding.tvProposalDate.text = "Data: ${proposal.date}"
            binding.tvProposalBudget.text = proposal.budget

            // Status badge
            binding.tvProposalStatus.text = proposal.status.label
            binding.tvProposalStatus.setTextColor(Color.parseColor(proposal.status.colorHex))

            // Show accept/reject buttons only for models with pending proposals
            if (!isCompany && proposal.status == ProposalStatus.PENDING) {
                binding.actionButtons.visibility = View.VISIBLE
                binding.btnAccept.setOnClickListener { onAccept(proposal) }
                binding.btnReject.setOnClickListener { onReject(proposal) }
            } else {
                binding.actionButtons.visibility = View.GONE
            }

            binding.root.setOnClickListener { onClick(proposal) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemProposalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    class DiffCallback : DiffUtil.ItemCallback<Proposal>() {
        override fun areItemsTheSame(a: Proposal, b: Proposal) = a.id == b.id
        override fun areContentsTheSame(a: Proposal, b: Proposal) = a == b
    }
}
