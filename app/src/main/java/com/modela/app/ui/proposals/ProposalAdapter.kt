package com.modela.app.ui.proposals

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.modela.app.data.model.Proposal
import com.modela.app.data.model.ProposalStatus
import com.modela.app.databinding.ItemProposalBinding
import com.modela.app.util.CompanyVisualHelper
import com.modela.app.util.loadImage

class ProposalAdapter(
    private val isCompany: Boolean,
    private val onAccept: (Proposal) -> Unit,
    private val onReject: (Proposal) -> Unit,
    private val onAdvanceAcceptedStage: (Proposal) -> Unit,
    private val onClick: (Proposal) -> Unit
) : ListAdapter<Proposal, ProposalAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(val binding: ItemProposalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(proposal: Proposal) {
            // Show model info if company, company info if model
            if (isCompany) {
                binding.tvProposalName.text = proposal.modelName
                binding.ivProposalPhoto.loadImage(proposal.modelImageUrl)
                binding.ivProposalCompanyLogo.visibility = View.VISIBLE
                binding.ivProposalCompanyLogo.setImageResource(CompanyVisualHelper.logoForCompany(proposal.companyName))
            } else {
                binding.tvProposalName.text = proposal.companyName
                binding.ivProposalPhoto.setImageResource(CompanyVisualHelper.logoForCompany(proposal.companyName))
                binding.ivProposalCompanyLogo.visibility = View.GONE
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
            configureModelStagePanels(proposal)

            binding.actionButtons.visibility = View.GONE

            binding.root.setOnClickListener { onClick(proposal) }
        }

        private fun configureModelStagePanels(proposal: Proposal) {
            binding.pendingAnalysisPanel.visibility = View.GONE
            binding.acceptedJourneyPanel.visibility = View.GONE
            binding.completedReviewPanel.visibility = View.GONE

            if (isCompany) return

            when (proposal.status) {
                ProposalStatus.PENDING -> binding.pendingAnalysisPanel.visibility = View.VISIBLE
                ProposalStatus.ACCEPTED -> bindAcceptedJourney(proposal)
                ProposalStatus.COMPLETED -> bindCompletedReview(proposal)
                else -> Unit
            }
        }

        private fun bindAcceptedJourney(proposal: Proposal) {
            binding.acceptedJourneyPanel.visibility = View.VISIBLE
            binding.tvJourneyDate.text = "Data\n${proposal.date}"
            binding.tvJourneyLocation.text = "Local\n${proposal.location}"
            binding.tvJourneyStatus.text = proposal.acceptedStage.label
            binding.tvJourneyStatus.setTextColor(Color.parseColor(proposal.acceptedStage.colorHex))
            binding.tvJourneyHint.text = if (proposal.acceptedStage.name == "SCOUTING") {
                "Scouting confirmado. Prepare portfolio, rota e lembrete para a primeira conversa."
            } else {
                "Job confirmado. Check-in recomendado 30 min antes e equipe em modo acompanhamento."
            }
            binding.btnAdvanceAcceptedStage.text = if (proposal.acceptedStage.name == "SCOUTING") {
                "Avancar para Job"
            } else {
                "Concluir Job"
            }

            binding.btnAddReminder.setOnClickListener {
                pulse(it)
                Toast.makeText(
                    binding.root.context,
                    "Lembrete visual criado para ${proposal.date}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            binding.btnOpenRoute.setOnClickListener {
                pulse(it)
                Toast.makeText(
                    binding.root.context,
                    "Rota preparada para ${proposal.location}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            binding.btnEmergency.setOnClickListener {
                pulse(it)
                Toast.makeText(
                    binding.root.context,
                    "Alerta enviado para contato de seguranca e equipe do job",
                    Toast.LENGTH_SHORT
                ).show()
            }

            binding.btnAdvanceAcceptedStage.setOnClickListener {
                pulse(it)
                onAdvanceAcceptedStage(proposal)
            }
        }

        private fun bindCompletedReview(proposal: Proposal) {
            binding.completedReviewPanel.visibility = View.VISIBLE
            binding.tvCompletedTitle.text = "Job concluido com ${proposal.companyName}"

            binding.btnRateExperience.setOnClickListener {
                Toast.makeText(
                    binding.root.context,
                    "Avaliacao registrada para demonstracao",
                    Toast.LENGTH_SHORT
                ).show()
            }

            binding.star1.setOnClickListener { setRating(1, true) }
            binding.star2.setOnClickListener { setRating(2, true) }
            binding.star3.setOnClickListener { setRating(3, true) }
            binding.star4.setOnClickListener { setRating(4, true) }
            binding.star5.setOnClickListener { setRating(5, true) }
            setRating(0, false)

            binding.btnViewReceipt.setOnClickListener {
                pulse(it)
                Toast.makeText(
                    binding.root.context,
                    "Resumo visual: cache, taxa e pagamento liberado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        private fun pulse(view: View) {
            view.animate()
                .scaleX(0.96f)
                .scaleY(0.96f)
                .setDuration(80L)
                .withEndAction {
                    view.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(120L)
                        .start()
                }
                .start()
        }

        private fun setRating(rating: Int, animate: Boolean) {
            val stars = listOf(
                binding.star1,
                binding.star2,
                binding.star3,
                binding.star4,
                binding.star5
            )
            val yellow = Color.parseColor("#F5C542")
            stars.forEachIndexed { index, star ->
                star.setTextColor(if (index < rating) yellow else Color.WHITE)
            }
            if (animate) pulse(binding.ratingPreview)
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
