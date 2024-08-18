package com.example.chat_app_compose.presentation.common

import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.chat_app_compose.ui.theme.grey1
import com.example.chat_app_compose.ui.theme.grey2

@Composable
fun MyTextField(onClick:(String)->Unit, startValue:String){
    TextField(value = startValue, onValueChange = {onClick(it)}, colors = TextFieldDefaults.colors(
        focusedContainerColor = grey1,
        unfocusedContainerColor = grey1,
        unfocusedPlaceholderColor = Color.Transparent,
        focusedPlaceholderColor = Color.Transparent,
        focusedTextColor = grey2,
        unfocusedTextColor = grey2,
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedLabelColor = grey2,
        focusedLabelColor = grey2
    ))
}