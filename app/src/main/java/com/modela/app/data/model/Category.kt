package com.modela.app.data.model

data class Category(
    val id: String = "",
    val name: String = "",
    val isSelected: Boolean = false
)

data class Review(
    val id: String = "",
    val authorName: String = "",
    val authorAvatar: String = "",
    val rating: Float = 0f,
    val comment: String = "",
    val timestamp: Long = 0L
)
