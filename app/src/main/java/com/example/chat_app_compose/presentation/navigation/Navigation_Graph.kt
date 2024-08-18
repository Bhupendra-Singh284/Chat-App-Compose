package com.example.chat_app_compose.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.chat_app_compose.presentation.login.LoginScreen
import com.example.chat_app_compose.presentation.login.LoginViewModel
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun NavigationGraph(
    startDestination:String
){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination){
        navigation(startDestination = Routes.LoginScreen.route, route = Routes.UserAuthGraph.route){
            composable(route=Routes.LoginScreen.route){
                val loginViewModel:LoginViewModel = hiltViewModel()
                LoginScreen()
            }
            composable(route=Routes.SignupScreen.route){}
            composable(route=Routes.ForgotPasswordScreen.route){}
        }
        navigation(startDestination = Routes.HomeScreen.route, route = Routes.UserChatGraph.route){
            composable(route=Routes.HomeScreen.route){
                Column(
                    Modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center)) {
                    Text(text = "Home Screen")
                }
            }
            composable(route=Routes.NotificationScreen.route){}
            composable(route=Routes.ProfileScreen.route){}
        }
    }
}