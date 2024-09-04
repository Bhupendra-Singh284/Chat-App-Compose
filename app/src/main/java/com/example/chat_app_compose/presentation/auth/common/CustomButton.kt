package com.example.chat_app_compose.presentation.auth.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.chat_app_compose.theme.green2
import com.example.chat_app_compose.util.Sizes
import kotlinx.coroutines.launch

@Composable
fun CustomButton(modifier: Modifier=Modifier,text:String, containerColor:Color=green2,textColor:Color=Color.White,onCLick:()->Unit){
    Button(
        shape = RoundedCornerShape(Sizes.authButtonCorner),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = textColor
        ),
        contentPadding = PaddingValues(Sizes.paddingXSmall),
        modifier = modifier.fillMaxWidth(), onClick = {
            onCLick()
    }) {
        Text(text = text, style = MaterialTheme.typography.labelMedium)
    }

}