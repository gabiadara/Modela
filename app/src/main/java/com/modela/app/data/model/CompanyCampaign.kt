package com.modela.app.data.model

data class CompanyCampaign(
    val id: String,
    val title: String,
    val type: String,
    val coverImageUrl: String,
    val description: String,
    val location: String,
    val date: String,
    val budget: String,
    val status: String,
    val tags: List<String>,
    val applicants: Int
)
