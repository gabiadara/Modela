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
    val acceptedStage: AcceptedProposalStage = AcceptedProposalStage.SCOUTING,
    val timestamp: Long = System.currentTimeMillis()
)

enum class ProposalStatus(val label: String, val colorHex: String) {
    PENDING("Pendente", "#F5A623"),
    ACCEPTED("Aceita", "#4F8A6B"),
    REJECTED("Recusada", "#D64B5F"),
    COMPLETED("Concluida", "#5B7FFF")
}

enum class AcceptedProposalStage(val label: String, val colorHex: String) {
    SCOUTING("Scouting", "#8B6F3D"),
    JOB("Job", "#3B6A8C")
}
