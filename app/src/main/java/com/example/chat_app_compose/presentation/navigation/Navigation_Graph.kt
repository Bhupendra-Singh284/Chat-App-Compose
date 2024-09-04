package com.example.chat_app_compose.presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.chat_app_compose.presentation.auth.login.LoginScreen
import com.example.chat_app_compose.presentation.auth.login.LoginViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.chat_app_compose.presentation.auth.email_verify.EmailVerificationScreen
import com.example.chat_app_compose.presentation.auth.email_verify.EmailVerificationViewModel
import com.example.chat_app_compose.presentation.auth.forgot_password.ForgotPasswordScreen
import com.example.chat_app_compose.presentation.auth.forgot_password.ForgotPasswordViewModel
import com.example.chat_app_compose.presentation.auth.sign_up.SignUpScreen
import com.example.chat_app_compose.presentation.auth.sign_up.SignUpViewModel


@Composable
fun NavigationGraph(
    startDestination:String,
    changeStartDestination:(String)->Unit
){

    Log.d("MainActivity","nav graph called, startDestination ->$startDestination")
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        navigation(
            startDestination = Routes.LoginScreen.route,
            route = Routes.UserAuthGraph.route
        ){
            composable(route=Routes.LoginScreen.route)
            {
                val loginViewModel: LoginViewModel = hiltViewModel()
                LoginScreen(
                    emailVerified = loginViewModel.emailVerified,
                    navigateToEmailVerify = { navigateToScreen(navController,Routes.EmailVerificationScreen.route) },
                    loginState = loginViewModel.loginState,
                    authState = loginViewModel.resultState,
                    resetAuthState = loginViewModel::resetAuthState,
                    navigateToHome = { changeStartDestination(Routes.UserLoggedInGraph.route) },
                    onEvent = loginViewModel::onLoginScreenEvent,
                    onSignUpClick = { navigateToScreen(navController,Routes.SignupScreen.route) },
                    onForgotPasswordClick = { navigateToScreen(navController,Routes.ForgotPasswordScreen.route) }
                )
            }

             composable(route=Routes.SignupScreen.route)
            {
                val signUpViewModel =  hiltViewModel<SignUpViewModel>()
                Log.d("MainActivity","sign up nav composable called, authState-> ${signUpViewModel.resultState}")
                SignUpScreen(
                    userVerified = signUpViewModel.isUserVerified,
                    authState = signUpViewModel.resultState,
                    resetAuthState = signUpViewModel::resetAuthState,
                    signUpState = signUpViewModel.signUpState,
                    onBackClick = { navController.popBackStack() },
                    onEvent = signUpViewModel::onSignUpEvent,
                    navigateToHome = {changeStartDestination(Routes.UserLoggedInGraph.route)},
                    navigateToEmailVerify = { navigateToScreen(navController,Routes.EmailVerificationScreen.route) }
                )
            }

            composable(route=Routes.ForgotPasswordScreen.route)
            {
                val viewModel= hiltViewModel<ForgotPasswordViewModel>()
                ForgotPasswordScreen(
                    authState = viewModel.resultState,
                    resetAuthState = viewModel::resetAuthState,
                    forgotPasswordState =viewModel.forgotPasswordState,
                    onEvent = viewModel::onEvent,
                    onBackClick =  { navController.popBackStack()}
                )
            }
            composable(route=Routes.EmailVerificationScreen.route)
            {
                val viewModel= hiltViewModel<EmailVerificationViewModel>()
                EmailVerificationScreen(
                    isUserVerified = viewModel.isUserVerified,
                    verificationLinkSent = viewModel.verificationLinkSent,
                    userEmail = viewModel.getUserEmail(),
                    authState = viewModel.resultState,
                    onBackClick = { navController.popBackStack()},
                    sendLink = { viewModel.sendVerificationLink()},
                    navigateToHome = {changeStartDestination(Routes.UserLoggedInGraph.route)},
                    onContinue = viewModel::userContinue,
                    resetAuthState = viewModel::resetState)
            }
        }
        navigation(
            startDestination = Routes.HomeScreen.route,
            route = Routes.UserLoggedInGraph.route
        ){
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

fun navigateToScreen(navController: NavController,route:String){
    navController.navigate(route){
        launchSingleTop=true
    }
}