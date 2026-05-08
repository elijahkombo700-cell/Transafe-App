package com.elijah.transafe.models

class SafetyTip{
    var id: String = ""
    var title: String = ""
    var description: String = ""
    var category: String = ""

constructor(id: String, title: String, description: String, category: String) {
    this.id = id
    this.title = title
    this.description = description
    this.category = category

}
    constructor()
}
