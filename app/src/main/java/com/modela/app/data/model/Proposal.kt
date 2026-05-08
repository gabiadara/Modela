package com.modela.app.data.model

data class Proposal(
    val id: String = "",
    val modelName: String = "",
    val modelImageUrl: String = "",
    val companyName: String = "",
    val companyImageUrl: String = "",
    val jobTitle: String = "",
    val jobDescription: String = "",
    val category: String = "",
    val location: String = "",
    val date: String = "",
    val budget: String = "",
    val status: ProposalStatus = ProposalStatus.PENDING,
    val acceptedStage: AcceptedProposalStage = AcceptedProposalStage.CASTING,
    val timestamp: Long = System.currentTimeMillis()
)

enum class ProposalStatus(val label: String, val colorHex: String) {
    PENDING("Pendente", "#F5A623"),
    ACCEPTED("Aceita", "#4F8A6B"),
    REJECTED("Recusada", "#D64B5F"),
    COMPLETED("Concluida", "#5B7FFF")
}

enum class AcceptedProposalStage(val label: String, val colorHex: String) {
    CASTING("Casting", "#3B6A8C"),
    JOB("Job", "#3B6A8C")
}
