package com.modela.app.ui.proposals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.modela.app.data.model.Proposal
import com.modela.app.data.model.ProposalStatus
import com.modela.app.data.repository.MockDataProvider

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

    private fun loadProposals() {
        allProposals = MockDataProvider.getProposals()
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
        allProposals = allProposals.map {
            if (it.id == proposal.id) it.copy(status = ProposalStatus.ACCEPTED) else it
        }
        _proposals.value = allProposals
        filterByStatus(currentFilter)
    }

    fun rejectProposal(proposal: Proposal) {
        allProposals = allProposals.map {
            if (it.id == proposal.id) it.copy(status = ProposalStatus.REJECTED) else it
        }
        _proposals.value = allProposals
        filterByStatus(currentFilter)
    }
}
