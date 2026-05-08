package com.elijah.transafe.models

class Trip {
    var id: String = ""
    var userId: String = ""
    var startTime: Long = 0L
    var endTime: Long = 0L
    var distance: Double = 0.0
    var safetyScore: Int = 0
    var startLocation: String = ""
    var endLocation: String = ""

    constructor(
        id: String,
        userId: String,
        startTime: Long,
        endTime: Long,
        distance: Double,
        safetyScore: Int,
        startLocation: String,
        endLocation: String
    ) {
        this.id = id
        this.userId = userId
        this.startTime = startTime
        this.endTime = endTime
        this.distance = distance
        this.safetyScore = safetyScore
        this.startLocation = startLocation
        this.endLocation = endLocation
    }

    constructor()
}
