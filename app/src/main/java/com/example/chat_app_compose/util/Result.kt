package com.example.chat_app_compose.util

sealed class MyResult {
    data object Loading:MyResult()
    data object Success:MyResult()
    data class Failure(val error:String):MyResult()
}