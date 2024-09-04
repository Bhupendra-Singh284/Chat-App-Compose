package com.example.chat_app_compose.presentation.auth.common

import android.graphics.drawable.shapes.RoundRectShape
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.unit.dp
import com.example.chat_app_compose.theme.green1
import com.example.chat_app_compose.theme.grey1
import com.example.chat_app_compose.theme.grey3

@Composable
fun CustomBackButton(modifier:Modifier=Modifier,onBackClick:()->Unit) {
    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        Box(
            modifier=modifier.clip(shape = RoundedCornerShape(10.dp)).clickable {
                onBackClick()
            }
                .background(color = grey1)
                .size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier=Modifier.size(35.dp),
                imageVector = Icons.Rounded.KeyboardArrowLeft, contentDescription = null, tint = grey3)
        }
    }
}