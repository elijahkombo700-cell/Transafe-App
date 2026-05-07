package com.elijah.transafe.models

data class Trip(
    val id: String = "",
    val userId: String = "",
    val startTime: Long = 0L,
    val endTime: Long = 0L,
    val distance: Double = 0.0,
    val safetyScore: Int = 0,
    val startLocation: String = "",
    val endLocation: String = ""
)
