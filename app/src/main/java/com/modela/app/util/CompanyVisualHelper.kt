package com.modela.app.util

import com.modela.app.R

object CompanyVisualHelper {

    fun logoForJob(jobId: String): Int = when (jobId) {
        "j1" -> R.drawable.logo_editorial_house
        "j2" -> R.drawable.logo_luxe_studio
        "j3" -> R.drawable.logo_runway_week
        "j4" -> R.drawable.logo_beauty_natural
        "j5" -> R.drawable.logo_couture_atelier
        "j6" -> R.drawable.logo_wellness_mag
        else -> R.drawable.logo_editorial_house
    }

    fun logoForCompany(companyName: String): Int = when (companyName) {
        "Veyra Editorial" -> R.drawable.logo_editorial_house
        "Luma Studio" -> R.drawable.logo_luxe_studio
        "Aurora Runway" -> R.drawable.logo_runway_week
        "Botanika Lab" -> R.drawable.logo_beauty_natural
        "Altura Atelier" -> R.drawable.logo_couture_atelier
        "Pulse Forma" -> R.drawable.logo_wellness_mag
        else -> R.drawable.logo_editorial_house
    }
}
