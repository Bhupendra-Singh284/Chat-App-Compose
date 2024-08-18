package com.example.chat_app_compose.presentation.navigation

sealed class Routes(val route:String) {
    data object HomeScreen:Routes("HomeScreenRoute")
    data object ChatScreen:Routes("ChatScreenRoute")
    data object LoginScreen:Routes("LoginScreenRoute")
    data object UserAuthGraph:Routes("UserAuthenticationGraph")
    data object UserChatGraph:Routes("UserChatGraph")
    data object ForgotPasswordScreen:Routes("ForgotPasswordScreen")
    data object SignupScreen:Routes("SignUpScreenRoute")
    data object StoryScreen:Routes("StoryScreenRoute")
    data object ProfileScreen:Routes("ProfileScreenRoute")
    data object NotificationScreen:Routes("NotificationScreenRoute")
}