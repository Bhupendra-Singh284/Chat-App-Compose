package com.example.chat_app_compose.presentation.login

import com.example.chat_app_compose.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chat_app_compose.ui.theme.green1
import com.example.chat_app_compose.ui.theme.grey1
import com.example.chat_app_compose.util.paddingSmall

@Composable
fun LoginScreen(){
    Column(
        Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .statusBarsPadding()
            .padding(horizontal = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.app_icon_ui), contentDescription = "", modifier = Modifier.size(200.dp,160.dp))
        Spacer(modifier = Modifier.heightIn(paddingSmall))
        Text(text = "Welcome", color = green1, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(paddingSmall))
        Text(text = "Log in to your account", style = MaterialTheme.typography.titleMedium, color = grey2)
        Spacer(modifier = Modifier.height(paddingSmall))
        TextField(value = "", onValueChange = {}, colors = )
    }
}