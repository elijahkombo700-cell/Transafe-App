package com.elijah.transafe.models

data class Report(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val location: String = "",
    val timestamp: Long = 0L,
    val imageUrl: String = ""
)
