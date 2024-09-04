package com.example.chat_app_compose.presentation.auth.sign_up

import android.net.Uri
import androidx.credentials.Credential

sealed class SignUpEvent {
    data object OnSignUp:SignUpEvent()
    data class OnGoogleSignUp(val credential: Credential):SignUpEvent()

    data class UserImageChange(val newImage:Uri):SignUpEvent()
    data class UserNameValueChange(val newUserName:String): SignUpEvent()
    data class EmailValueChange(val newEmail:String): SignUpEvent()
    data class PasswordValueChange(val newPassword:String): SignUpEvent()

}