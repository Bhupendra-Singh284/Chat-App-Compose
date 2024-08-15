package com.example.chatappcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationGraph(
    startDestination:String
){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination){
        navigation(startDestination = Routes.LoginScreen.route, route = Routes.UserAuthGraph.route){
            composable(route=Routes.LoginScreen.route){}
            composable(route=Routes.SignupScreen.route){}
            composable(route=Routes.ForgotPasswordScreen.route){}
        }
        navigation(startDestination = Routes.HomeScreen.route, route = Routes.UserChatGraph.route){
            composable(route=Routes.HomeScreen.route){}
            composable(route=Routes.NotificationScreen.route){}
            composable(route=Routes.ProfileScreen.route){}
        }
    }
}