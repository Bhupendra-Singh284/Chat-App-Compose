package com.example.chat_app_compose.presentation.auth.forgot_password

sealed class ForgotPasswordEvent {
    data object SendCode:ForgotPasswordEvent()
    data class OnEmailChange(val newEmail:String):ForgotPasswordEvent()
}