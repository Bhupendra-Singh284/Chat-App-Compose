package com.example.chat_app_compose.presentation.auth.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.chat_app_compose.theme.green1
import com.example.chat_app_compose.theme.green2
import com.example.chat_app_compose.theme.grey1
import com.example.chat_app_compose.theme.grey2
import com.example.chat_app_compose.theme.grey3
import com.example.chat_app_compose.theme.lightBlue1
import com.example.chat_app_compose.theme.red1
import com.example.chat_app_compose.util.Sizes

@Composable
fun CustomTextField(modifier:Modifier=Modifier,onValueChange:(String)->Unit, startValue:String,label:String,labelIcon:ImageVector,errorText:String){
    OutlinedTextField(modifier = modifier.fillMaxWidth(), value = startValue, onValueChange = {onValueChange(it)},
        textStyle = MaterialTheme.typography.labelSmall,
        leadingIcon = {
            Icon(labelIcon, contentDescription = null, tint = grey2)
        },
        placeholder = {
            Text(text = label, color = grey2, style = MaterialTheme.typography.labelSmall)
        },
        supportingText = {
                         Text(text = errorText, style = MaterialTheme.typography.labelSmall.copy(fontSize = 13.sp, fontWeight = FontWeight.Normal), color = red1)
        },
        maxLines = 2,
        shape = RoundedCornerShape(Sizes.textFieldCorner),
        colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = grey1,
        unfocusedContainerColor = grey1, cursorColor = green2,
        unfocusedPlaceholderColor = Color.Transparent,
        focusedPlaceholderColor = Color.Transparent,
        focusedTextColor = grey2,
        unfocusedTextColor = grey2,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = green2.copy(alpha = 0.1f)
    ))
}