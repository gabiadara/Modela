package com.modela.app.ui.proposals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.modela.app.data.model.AcceptedProposalStage
import com.modela.app.data.model.Proposal
import com.modela.app.data.model.ProposalStatus

class ProposalsViewModel : ViewModel() {

    private val _proposals = MutableLiveData<List<Proposal>>()
    val proposals: LiveData<List<Proposal>> = _proposals

    private val _filteredProposals = MutableLiveData<List<Proposal>>()
    val filteredProposals: LiveData<List<Proposal>> = _filteredProposals

    private var currentFilter: ProposalStatus? = null
    private var allProposals: List<Proposal> = emptyList()

    init {
        loadProposals()
    }

    fun refresh() {
        loadProposals()
        filterByStatus(currentFilter)
    }

    private fun loadProposals() {
        allProposals = ProposalWorkflowStore.getProposals()
        _proposals.value = allProposals
        _filteredProposals.value = allProposals
    }

    fun filterByStatus(status: ProposalStatus?) {
        currentFilter = status
        _filteredProposals.value = if (status == null) {
            allProposals
        } else {
            allProposals.filter { it.status == status }
        }
    }

    fun acceptProposal(proposal: Proposal) {
        ProposalWorkflowStore.updateStatus(
            proposal.id,
            ProposalStatus.ACCEPTED,
            AcceptedProposalStage.SCOUTING
        )
        refresh()
    }

    fun rejectProposal(proposal: Proposal) {
        ProposalWorkflowStore.reject(proposal.id)
        refresh()
    }

    fun advanceAcceptedStage(proposal: Proposal): Proposal? {
        val updated = ProposalWorkflowStore.advanceAcceptedStage(proposal.id)
        refresh()
        return updated
    }
}
