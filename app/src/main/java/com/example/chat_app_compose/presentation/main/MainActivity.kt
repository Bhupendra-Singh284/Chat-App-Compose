package com.example.chat_app_compose.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.chat_app_compose.presentation.navigation.NavigationGraph
import com.example.chat_app_compose.presentation.navigation.Routes
import com.example.chat_app_compose.theme.ChatAppComposeTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity","On create activity called, Thread-> ${Thread.currentThread().name}, id-> ${Thread.currentThread().id}")
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
                    color = Color.White
                ) {
                    NavigationGraph(startDestination = mainViewModel.startDestination){
                        mainViewModel.changeStartDestination(it)
                    }
                }
            }
        }
    }
}
