package com.modela.app.ui.proposals

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.modela.app.R
import com.modela.app.data.model.Proposal
import com.modela.app.data.model.ProposalStatus
import com.modela.app.data.repository.MockDataProvider
import com.modela.app.databinding.FragmentProposalDetailBinding
import com.modela.app.util.loadImage

class ProposalDetailFragment : Fragment() {

    private var _binding: FragmentProposalDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProposalDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val proposalId = arguments?.getString("proposalId") ?: return
        val proposal = MockDataProvider.getProposals().find { it.id == proposalId } ?: return

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        populateJobInfo(proposal)
        buildTimeline(proposal)
        populatePayment(proposal)
        setupActions(proposal)

        binding.btnOpenMap.setOnClickListener {
            Toast.makeText(requireContext(), "📍 Abrindo localização...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun populateJobInfo(p: Proposal) {
        binding.ivDetailPhoto.loadImage(p.modelImageUrl)
        binding.tvDetailName.text = p.modelName
        binding.tvDetailCategory.text = p.category
        binding.tvDetailStatus.text = p.status.label
        binding.tvDetailStatus.setTextColor(Color.parseColor(p.status.colorHex))
        binding.tvDetailJobTitle.text = p.jobTitle
        binding.tvDetailJobDesc.text = p.jobDescription
        binding.tvDetailDate.text = "📅 ${p.date}"
        binding.tvDetailBudget.text = p.budget
        binding.tvDetailLocation.text = p.location
    }

    private fun buildTimeline(p: Proposal) {
        data class Step(val title: String, val subtitle: String, val state: Int) // 0=done, 1=active, 2=future

        val activeStep = when (p.status) {
            ProposalStatus.PENDING -> 1
            ProposalStatus.ACCEPTED -> 2
            ProposalStatus.REJECTED -> 1
            ProposalStatus.COMPLETED -> 5
        }

        val steps = listOf(
            Step("Proposta Enviada", "Empresa enviou a proposta", if (activeStep >= 1) 0 else 2),
            Step("Proposta Aceita", "Modelo analisa e aceita", if (activeStep >= 2) 0 else if (activeStep == 1) 1 else 2),
            Step("Depósito em Garantia", "Valor retido via Escrow", if (activeStep >= 3) 0 else if (activeStep == 2) 1 else 2),
            Step("Trabalho Realizado", "Sessão fotográfica concluída", if (activeStep >= 4) 0 else if (activeStep == 3) 1 else 2),
            Step("Pagamento Liberado", "Valor liberado ao modelo", if (activeStep >= 5) 0 else if (activeStep == 4) 1 else 2)
        )

        val container = binding.timelineContainer
        container.removeAllViews()

        steps.forEachIndexed { index, step ->
            val row = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.TOP
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }

            // Icon + Line column
            val iconCol = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER_HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(32.dp(), LinearLayout.LayoutParams.WRAP_CONTENT)
            }

            val icon = ImageView(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(24.dp(), 24.dp())
                setImageResource(
                    when (step.state) {
                        0 -> R.drawable.ic_check_circle
                        1 -> R.drawable.ic_pending_circle
                        else -> R.drawable.ic_inactive_circle
                    }
                )
            }
            iconCol.addView(icon)

            if (index < steps.size - 1) {
                val line = View(requireContext()).apply {
                    layoutParams = LinearLayout.LayoutParams(2.dp(), 32.dp()).apply {
                        topMargin = 4.dp()
                        bottomMargin = 4.dp()
                    }
                    setBackgroundColor(if (step.state == 0) Color.parseColor("#4F8A6B") else Color.parseColor("#D9D9D9"))
                }
                iconCol.addView(line)
            }
            row.addView(iconCol)

            // Text column
            val textCol = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).apply {
                    marginStart = 12.dp()
                }
            }

            val title = TextView(requireContext()).apply {
                text = step.title
                setTextColor(
                    when (step.state) {
                        0 -> Color.parseColor("#0E0E0E")
                        1 -> Color.parseColor("#F5A623")
                        else -> Color.parseColor("#8B8B8B")
                    }
                )
                textSize = 14f
                if (step.state <= 1) setTypeface(null, Typeface.BOLD)
            }
            textCol.addView(title)

            val subtitle = TextView(requireContext()).apply {
                text = step.subtitle
                setTextColor(Color.parseColor("#8B8B8B"))
                textSize = 11f
                setPadding(0, 2.dp(), 0, if (index < steps.size - 1) 8.dp() else 0)
            }
            textCol.addView(subtitle)

            row.addView(textCol)
            container.addView(row)
        }
    }

    private fun populatePayment(p: Proposal) {
        val budgetStr = p.budget.replace("R$ ", "").replace(".", "").replace(",", ".")
        val budget = budgetStr.toDoubleOrNull() ?: 0.0
        val fee = budget * 0.10
        val total = budget + fee

        binding.tvPayCache.text = p.budget
        binding.tvPayFee.text = "R$ ${String.format("%.0f", fee)}"
        binding.tvPayTotal.text = "R$ ${String.format("%.0f", total)}"
    }

    private fun setupActions(p: Proposal) {
        when (p.status) {
            ProposalStatus.PENDING -> {
                binding.btnDetailPrimary.text = "Aceitar Proposta"
                binding.btnDetailPrimary.setBackgroundResource(R.drawable.bg_button_accept)
                binding.btnDetailSecondary.text = "Recusar Proposta"
            }
            ProposalStatus.ACCEPTED -> {
                binding.btnDetailPrimary.text = "Enviar Mensagem"
                binding.btnDetailSecondary.text = "Ver Perfil"
            }
            ProposalStatus.COMPLETED -> {
                binding.btnDetailPrimary.text = "Avaliar Experiência"
                binding.btnDetailSecondary.text = "Enviar Mensagem"
            }
            ProposalStatus.REJECTED -> {
                binding.btnDetailPrimary.visibility = View.GONE
                binding.btnDetailSecondary.text = "Voltar às Propostas"
            }
        }

        binding.btnDetailPrimary.setOnClickListener {
            Toast.makeText(requireContext(), "Ação registrada com sucesso! ✅", Toast.LENGTH_SHORT).show()
        }
        binding.btnDetailSecondary.setOnClickListener {
            Toast.makeText(requireContext(), "Em breve", Toast.LENGTH_SHORT).show()
        }
    }

    private fun Int.dp(): Int = (this * resources.displayMetrics.density).toInt()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
