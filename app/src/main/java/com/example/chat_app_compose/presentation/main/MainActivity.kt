package com.example.chat_app_compose.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.chat_app_compose.presentation.navigation.NavigationGraph
import com.example.chat_app_compose.ui.theme.ChatAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val screen = installSplashScreen()
        val mainViewModel by viewModels<MainActivityViewModel>()
        screen.setKeepOnScreenCondition(condition = {
            mainViewModel.splashCondition.value
        })
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationGraph(startDestination = mainViewModel.startDestination.value)
                }
            }
        }
    }
}
