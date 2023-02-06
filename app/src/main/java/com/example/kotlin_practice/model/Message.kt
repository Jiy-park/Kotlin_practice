package com.example.kotlin_practice.model

class Message {
    var id:String = ""
    var msg:String = ""
    var userName:String = ""
    var timestamp:Long = 0L

    constructor()
    constructor(msg:String, userName: String){
        this.msg = msg
        this.userName = userName
        this.timestamp = System.currentTimeMillis()
    }
}