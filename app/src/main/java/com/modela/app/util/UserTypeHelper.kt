package com.modela.app.util

object UserTypeHelper {
    const val MODEL = "modelo"
    const val CONTRACTOR = "contratante"

    fun isModel(userType: String?): Boolean {
        return userType.equals(MODEL, ignoreCase = true) ||
            userType.equals("model", ignoreCase = true)
    }

    fun isContractor(userType: String?): Boolean {
        return userType.equals(CONTRACTOR, ignoreCase = true) ||
            userType.equals("contractor", ignoreCase = true) ||
            userType.equals("empresa", ignoreCase = true)
    }
}
