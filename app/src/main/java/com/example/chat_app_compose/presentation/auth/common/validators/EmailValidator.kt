package com.example.chat_app_compose.presentation.auth.common.validators

fun emailValidator(email:String):String{
    val result=""
    if(email.isEmpty()){
        return "Enter Email"
    }
    if(email.length<=5)
    {
        return "invalid email"
    }
    if(!email.contains("@")||!email.contains("mail.com")){
        return "invalid email"
    }
    return result
}