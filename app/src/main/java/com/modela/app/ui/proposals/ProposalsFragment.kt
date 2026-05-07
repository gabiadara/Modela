package com.modela.app.ui.proposals

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.modela.app.R
import com.modela.app.data.model.ProposalStatus
import com.modela.app.databinding.FragmentProposalsBinding
import com.modela.app.util.Constants

class ProposalsFragment : Fragment() {

    private var _binding: FragmentProposalsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProposalsViewModel by viewModels()

    private lateinit var adapter: ProposalAdapter
    private var isCompany = false
    private var selectedChip: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProposalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Read user type from preferences
        val prefs = requireContext().getSharedPreferences(Constants.PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        val userType = prefs.getString(Constants.KEY_USER_TYPE, "modelo") ?: "modelo"
        isCompany = userType.equals("empresa", ignoreCase = true)

        setupUI()
        setupAdapter()
        setupFilters()
        observeData()
    }

    private fun setupUI() {
        if (isCompany) {
            binding.tvTitle.text = "Minhas Propostas"
            binding.btnCreateProposal.visibility = View.VISIBLE
            binding.tvEmptySubtitle.text = "Crie sua primeira proposta para contratar um modelo"

            binding.btnCreateProposal.setOnClickListener {
                Toast.makeText(requireContext(), "Em breve: Criar nova proposta", Toast.LENGTH_SHORT).show()
            }
        } else {
            binding.tvTitle.text = "Propostas"
            binding.btnCreateProposal.visibility = View.GONE
            binding.tvEmptySubtitle.text = "Propostas de empresas aparecerão aqui"
        }
    }

    private fun setupAdapter() {
        adapter = ProposalAdapter(
            isCompany = isCompany,
            onAccept = { proposal ->
                viewModel.acceptProposal(proposal)
                Toast.makeText(requireContext(), "Proposta de ${proposal.companyName} aceita! ✅", Toast.LENGTH_SHORT).show()
            },
            onReject = { proposal ->
                viewModel.rejectProposal(proposal)
                Toast.makeText(requireContext(), "Proposta recusada", Toast.LENGTH_SHORT).show()
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
        selectedChip = binding.chipAll
        highlightChip(binding.chipAll)

        binding.chipAll.setOnClickListener { selectFilter(binding.chipAll, null) }
        binding.chipPending.setOnClickListener { selectFilter(binding.chipPending, ProposalStatus.PENDING) }
        binding.chipAccepted.setOnClickListener { selectFilter(binding.chipAccepted, ProposalStatus.ACCEPTED) }
        binding.chipCompleted.setOnClickListener { selectFilter(binding.chipCompleted, ProposalStatus.COMPLETED) }
    }

    private fun selectFilter(chip: TextView, status: ProposalStatus?) {
        resetChip(selectedChip)
        highlightChip(chip)
        selectedChip = chip
        viewModel.filterByStatus(status)
    }

    private fun highlightChip(chip: TextView?) {
        chip?.let {
            it.setTextColor(resources.getColor(com.modela.app.R.color.match_text_primary, null))
            it.setTypeface(null, Typeface.BOLD)
        }
    }

    private fun resetChip(chip: TextView?) {
        chip?.let {
            it.setTextColor(resources.getColor(com.modela.app.R.color.match_text_secondary, null))
            it.setTypeface(null, Typeface.NORMAL)
        }
    }

    private fun observeData() {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
