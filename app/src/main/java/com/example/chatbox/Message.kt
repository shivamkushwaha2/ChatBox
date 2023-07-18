package com.example.chatbox

class Message {
    var senderid:String? = null
    var message:String? = null
    constructor(){}
    constructor(message:String, senderid:String){
        this.message = message
        this.senderid = senderid
    }

}