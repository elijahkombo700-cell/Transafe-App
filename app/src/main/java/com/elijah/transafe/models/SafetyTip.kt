package com.elijah.transafe.models

data class SafetyTip(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = "" // e.g., "Speeding", "Weather", "Maintenance"
)
