package com.example.chat_app_compose.presentation.auth.sign_up

import android.net.Uri
import com.example.chat_app_compose.R

data class SignUpState(
    val userName:String="",
    val email:String="",
    val password:String="",
    val userNameErrorText:String="",
    val emailErrorText:String="",
    val passwordErrorText:String="",
    val imageUri: Uri = Uri.parse("android.resource://com.example.chat_app_compose/drawable/" + R.drawable.person)
) {
}