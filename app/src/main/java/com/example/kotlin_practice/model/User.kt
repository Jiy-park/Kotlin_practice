package com.example.kotlin_practice.model

class User {
    var name:String = ""
    var id:String = ""
    var password:String=  ""

    constructor()
    constructor(id:String, password:String, name:String){
        this.id = id
        this.password = password
        this.name = name
    }
}