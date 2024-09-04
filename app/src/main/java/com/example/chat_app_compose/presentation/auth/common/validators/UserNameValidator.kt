package com.example.chat_app_compose.presentation.auth.common.validators

fun userNameValidator(userName:String):String{
    val result=""

    if(userName.isEmpty())
    {
        return "Enter User name "
    }

    if(userName.length<=4)
    {
        return "User name too short"
    }

    if(userName.contains(" "))
    {
        return "Invalid user name"
    }
    return result
}