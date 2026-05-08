package com.elijah.transafe.models

class Report{
    var id: String = ""
    var userId: String = ""
    var title: String = ""
    var description: String = ""
    var location: String = ""
    var timestamp: Long = 0L
    var imageUrl: String = ""

constructor(id: String, userId: String, title: String, description: String, location: String, timestamp: Long, imageUrl: String) {
    this.id = id
    this.userId = userId
    this.title = title
    this.description = description
    this.location = location
    this.timestamp = timestamp
    this.imageUrl = imageUrl

}
    constructor()
}