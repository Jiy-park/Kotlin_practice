package com.example.kotlin_practice.model

class Room {
    var id:String = ""
    var title:String = ""
    var users:String = ""

    constructor()
    constructor(title:String, creatorName:String){
        this.title = title
        this.users = creatorName
    }
}