package com.modela.app.data.model

data class JobOpening(
    val id: String = "",
    val companyName: String = "",
    val companyImageUrl: String = "",
    val campaignImageUrl: String = "",
    val companyLocation: String = "",
    val jobTitle: String = "",
    val jobDescription: String = "",
    val fullDescription: String = "",
    val category: String = "",
    val location: String = "",
    val date: String = "",
    val budget: String = "",
    val tags: List<JobTag> = emptyList(),
    val requirements: List<String> = emptyList(),
    val postedAt: Long = System.currentTimeMillis(),
    val applicants: Int = 0,
    val isRemote: Boolean = false
)

data class JobTag(
    val label: String,
    val type: JobTagType
)

enum class JobTagType {
    URGENT,      // vermelho
    SEASON,      // azul
    CATEGORY,    // cinza escuro
    EXCLUSIVE,   // dourado
    NEW          // verde
}
