package com.modela.app.ui.proposals

import com.modela.app.data.model.AcceptedProposalStage
import com.modela.app.data.model.Proposal
import com.modela.app.data.model.ProposalStatus
import com.modela.app.data.repository.MockDataProvider

object ProposalWorkflowStore {
    private var proposals: List<Proposal> = MockDataProvider.getProposals()

    fun getProposals(): List<Proposal> = proposals

    fun findProposal(id: String): Proposal? = proposals.find { it.id == id }

    fun updateStatus(
        proposalId: String,
        status: ProposalStatus,
        acceptedStage: AcceptedProposalStage = AcceptedProposalStage.CASTING
    ): Proposal? {
        var updated: Proposal? = null
        proposals = proposals.map { proposal ->
            if (proposal.id == proposalId) {
                proposal.copy(status = status, acceptedStage = acceptedStage).also { updated = it }
            } else {
                proposal
            }
        }
        return updated
    }

    fun reject(proposalId: String): Proposal? = updateStatus(proposalId, ProposalStatus.REJECTED)

    fun advanceAcceptedStage(proposalId: String): Proposal? {
        var updated: Proposal? = null
        proposals = proposals.map { proposal ->
            if (proposal.id == proposalId && proposal.status == ProposalStatus.ACCEPTED) {
                val next = when (proposal.acceptedStage) {
                    AcceptedProposalStage.CASTING -> proposal.copy(
                        acceptedStage = AcceptedProposalStage.JOB
                    )
                    AcceptedProposalStage.JOB -> proposal.copy(
                        status = ProposalStatus.COMPLETED,
                        acceptedStage = AcceptedProposalStage.JOB
                    )
                }
                updated = next
                next
            } else {
                proposal
            }
        }
        return updated
    }
}
