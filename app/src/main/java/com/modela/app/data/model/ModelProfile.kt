package com.modela.app.data.model

data class ModelProfile(
    val id: String = "",
    val name: String = "",
    val profileImageUrl: String = "",
    val category: String = "",
    val bio: String = "",
    val rating: Float = 0f,
    val jobsCompleted: Int = 0,
    val followers: Int = 0,
    val height: String = "",
    val weight: String = "",
    val eyeColor: String = "",
    val hairColor: String = "",
    val bust: String = "",
    val waist: String = "",
    val hips: String = "",
    val shoeSize: String = "",
    val photos: List<String> = emptyList(),
    val socialMedia: Map<String, String> = emptyMap(),
    val isFavorite: Boolean = false,
    val location: String = ""
)
