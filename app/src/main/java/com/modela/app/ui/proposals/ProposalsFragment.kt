package com.modela.app.ui.proposals

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.modela.app.R
import com.modela.app.data.model.ProposalStatus
import com.modela.app.data.repository.MockDataProvider
import com.modela.app.databinding.FragmentProposalsBinding
import com.modela.app.util.Constants
import com.modela.app.util.UserTypeHelper

class ProposalsFragment : Fragment() {

    private var _binding: FragmentProposalsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProposalsViewModel by viewModels()

    private lateinit var adapter: ProposalAdapter
    private lateinit var companyCampaignAdapter: CompanyCampaignAdapter
    private var isCompany = false
    private var selectedChip: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProposalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences(
            Constants.PREF_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
        val userType = prefs.getString(Constants.KEY_USER_TYPE, UserTypeHelper.MODEL)
        isCompany = UserTypeHelper.isContractor(userType)

        setupUI()
        setupAdapter()
        setupFilters()
        observeData()
    }

    private fun setupUI() {
        if (isCompany) {
            binding.tvHeaderKicker.text = "Painel da empresa"
            binding.tvTitle.text = "Minhas campanhas"
            binding.tvHeaderSubtitle.text = "Publique castings e acompanhe perfis recebidos"
            binding.tvProposalCount.text = "3 ativas"
            binding.layoutCreateActions.visibility = View.VISIBLE
            binding.proposalFilterScroll.visibility = View.GONE
            binding.rvProposals.visibility = View.GONE
            binding.rvCompanyCampaigns.visibility = View.VISIBLE
            binding.emptyState.visibility = View.GONE
            binding.tvEmptySubtitle.text = "Suas campanhas aparecerao aqui"

            binding.btnCreateCampaign.setOnClickListener { openCreateCampaign("Campanha") }
        } else {
            binding.tvHeaderKicker.text = "Castings"
            binding.tvTitle.text = "Propostas"
            binding.tvHeaderSubtitle.text = "Briefings, campanhas e convites selecionados"
            binding.layoutCreateActions.visibility = View.GONE
            binding.proposalFilterScroll.visibility = View.VISIBLE
            binding.rvCompanyCampaigns.visibility = View.GONE
            binding.tvEmptySubtitle.text = "Propostas de empresas aparecerao aqui"
        }
    }

    private fun setupAdapter() {
        if (isCompany) {
            companyCampaignAdapter = CompanyCampaignAdapter { campaign ->
                val bundle = Bundle().apply { putString("campaignId", campaign.id) }
                findNavController().navigate(R.id.action_proposals_to_campaignCandidates, bundle)
            }
            binding.rvCompanyCampaigns.layoutManager = LinearLayoutManager(requireContext())
            binding.rvCompanyCampaigns.adapter = companyCampaignAdapter
            return
        }

        adapter = ProposalAdapter(
            isCompany = isCompany,
            onAccept = { proposal ->
                viewModel.acceptProposal(proposal)
                Toast.makeText(
                    requireContext(),
                    "Proposta de ${proposal.companyName} aceita",
                    Toast.LENGTH_SHORT
                ).show()
            },
            onReject = { proposal ->
                viewModel.rejectProposal(proposal)
                Toast.makeText(requireContext(), "Proposta recusada", Toast.LENGTH_SHORT).show()
            },
            onAdvanceAcceptedStage = { proposal ->
                val updated = viewModel.advanceAcceptedStage(proposal)
                val message = when (updated?.status) {
                    ProposalStatus.COMPLETED -> "Job movido para concluidos"
                    ProposalStatus.ACCEPTED -> "Etapa atualizada para ${updated.acceptedStage.label}"
                    else -> "Etapa atualizada"
                }
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            },
            onClick = { proposal ->
                val bundle = Bundle().apply { putString("proposalId", proposal.id) }
                findNavController().navigate(R.id.action_proposals_to_detail, bundle)
            }
        )

        binding.rvProposals.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProposals.adapter = adapter
    }

    private fun setupFilters() {
        if (isCompany) return

        selectedChip = binding.chipPending
        highlightChip(binding.chipPending)

        binding.chipPending.setOnClickListener {
            selectFilter(binding.chipPending, ProposalStatus.PENDING)
        }
        binding.chipAccepted.setOnClickListener {
            selectFilter(binding.chipAccepted, ProposalStatus.ACCEPTED)
        }
        binding.chipCompleted.setOnClickListener {
            selectFilter(binding.chipCompleted, ProposalStatus.COMPLETED)
        }
    }

    private fun selectFilter(chip: TextView, status: ProposalStatus?) {
        resetChip(selectedChip)
        highlightChip(chip)
        selectedChip = chip
        viewModel.filterByStatus(status)
    }

    private fun highlightChip(chip: TextView?) {
        chip?.let {
            it.isSelected = true
            it.setTextColor(resources.getColor(R.color.match_white, null))
            it.setTypeface(null, Typeface.BOLD)
        }
    }

    private fun resetChip(chip: TextView?) {
        chip?.let {
            it.isSelected = false
            it.setTextColor(resources.getColor(R.color.match_text_secondary, null))
            it.setTypeface(null, Typeface.NORMAL)
        }
    }

    private fun openCreateCampaign(type: String) {
        val bundle = Bundle().apply { putString("campaignType", type) }
        findNavController().navigate(R.id.action_proposals_to_createCampaign, bundle)
    }

    private fun observeData() {
        if (isCompany) {
            val campaigns = MockDataProvider.getCompanyCampaigns()
            companyCampaignAdapter.submitList(campaigns)
            binding.tvProposalCount.text = "${campaigns.size} ativas"
            return
        }

        viewModel.filteredProposals.observe(viewLifecycleOwner) { proposals ->
            adapter.submitList(proposals)

            val total = viewModel.proposals.value?.size ?: 0
            binding.tvProposalCount.text = "$total propostas"

            if (proposals.isEmpty()) {
                binding.emptyState.visibility = View.VISIBLE
                binding.rvProposals.visibility = View.GONE
            } else {
                binding.emptyState.visibility = View.GONE
                binding.rvProposals.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isCompany && ::adapter.isInitialized) {
            viewModel.refresh()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
