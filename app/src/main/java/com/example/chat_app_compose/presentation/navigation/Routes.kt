package com.example.chat_app_compose.presentation.navigation

sealed class Routes(val route:String) {

    //nav graphs
    data object UserAuthGraph:Routes("UserAuthenticationGraph")
    data object UserLoggedInGraph:Routes("UserChatGraph")

    //auth screens
    data object LoginScreen:Routes("LoginScreenRoute")
    data object SignupScreen:Routes("SignUpScreenRoute")
    data object ForgotPasswordScreen:Routes("ForgotPasswordScreen")
    data object EmailVerificationScreen:Routes("EmailVerificationScreen")

    // user logged in screens

    data object HomeScreen:Routes("HomeScreenRoute")
    data object ChatScreen:Routes("ChatScreenRoute")
    data object StoryScreen:Routes("StoryScreenRoute")
    data object ProfileScreen:Routes("ProfileScreenRoute")
    data object NotificationScreen:Routes("NotificationScreenRoute")
}