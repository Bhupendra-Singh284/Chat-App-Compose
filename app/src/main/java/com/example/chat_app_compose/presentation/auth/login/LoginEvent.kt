package com.example.chat_app_compose.presentation.auth.login

import androidx.credentials.Credential

sealed class LoginEvent {
    data object UserLogIn: LoginEvent()
    data class UserGoogleSignIn(val credential: Credential): LoginEvent()
    data class EmailValueChange(val newEmail:String):LoginEvent()
    data class PasswordValueChange(val newPassword:String):LoginEvent()
}