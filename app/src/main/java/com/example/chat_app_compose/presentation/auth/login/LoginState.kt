package com.example.chat_app_compose.presentation.auth.login

data class LoginState(
    val emailText:String="",
    val passwordText:String="",
    val emailErrorText:String="",
    val passwordErrorText:String="") {
}