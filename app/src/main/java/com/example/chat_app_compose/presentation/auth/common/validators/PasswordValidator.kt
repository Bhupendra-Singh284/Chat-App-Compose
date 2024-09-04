package com.example.chat_app_compose.presentation.auth.common.validators

fun passwordValidator(password:String):String{
    val result=""
    if(password.isEmpty())
    {
        return "Enter Password"
    }

    if(password.length<=7)
    {
        return "Password too short"
    }

    if(password.length>=20)
    {
        return "Password too long"
    }
    return result
}