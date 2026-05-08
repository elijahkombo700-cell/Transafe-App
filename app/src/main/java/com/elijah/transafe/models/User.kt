package com.elijah.transafe.models

class User {
    var id: String = ""
    var name: String = ""
    var plateNumber: String = ""
    var profileImageUrl: String = ""

    constructor(id: String, name: String, plateNumber: String, profileImageUrl: String) {
        this.id = id
        this.name = name
        this.plateNumber = plateNumber
        this.profileImageUrl = profileImageUrl
    }

    constructor()
    }
