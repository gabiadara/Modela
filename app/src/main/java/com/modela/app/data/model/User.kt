package com.modela.app.data.model

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val userType: UserType = UserType.MODEL,
    val profileImageUrl: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

enum class UserType { MODEL, CONTRACTOR }
