package com.example.chat_app_compose.presentation.auth.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.Credential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat_app_compose.data.remote.AuthServices
import com.example.chat_app_compose.presentation.auth.common.validators.emailValidator
import com.example.chat_app_compose.presentation.auth.common.validators.passwordValidator
import com.example.chat_app_compose.util.MyResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authServices: AuthServices
):ViewModel() {

    var loginState by mutableStateOf(LoginState())
        private set
    var resultState:MyResult? by mutableStateOf(null)
        private set
    var emailVerified=false
        private set

    override fun onCleared() {
        super.onCleared()
        Log.d("MainActivity", "onCleared  Login")
    }

    fun onLoginScreenEvent(event: LoginEvent){
        when(event)
        {
            is LoginEvent.UserLogIn ->{
                onLoginClick()
            }
            is LoginEvent.UserGoogleSignIn ->{
                onSignInWithGoogle(event.credential)
            }
            is LoginEvent.EmailValueChange ->{
                onEmailChange(event.newEmail)
            }
            is LoginEvent.PasswordValueChange ->{
                onPasswordChange(event.newPassword)
            }
        }
    }

    private fun onLoginClick(){

        val emailValidationResult= emailValidator(loginState.emailText)

        if(emailValidationResult!=""){
            loginState =loginState.copy(emailErrorText = emailValidationResult)
            return
        }

        val passwordValidationResult= passwordValidator(loginState.passwordText)

        if(passwordValidationResult!=""){
            loginState = loginState.copy(passwordErrorText=passwordValidationResult)
            return
        }

        resultState= MyResult.Loading
        viewModelScope.launch {
            val result:String? =authServices.loginUser(loginState.emailText,loginState.passwordText)
            resultState = if(result==null) {
                emailVerified = authServices.isUserEmailVerified()
                if(!emailVerified)
                {
                    authServices.sendEmailVerificationMail()
                }
                MyResult.Success
            } else{
                MyResult.Failure(result)
            }
        }
    }

//    fun isUserVerified():Boolean{
//        var result=false
//        viewModelScope.launch {
//            result=authServices.isUserEmailVerified()
//        }
//        return result
//    }

    fun resetAuthState(state:MyResult?){
        resultState=state
    }
    private fun onSignInWithGoogle(credential: Credential){
        resultState=MyResult.Loading
        viewModelScope.launch {
            val result=authServices.signInWithGoogle(credential)
            resultState=if(result==null)
            {
                emailVerified=true
                MyResult.Success
            }else{
                MyResult.Failure(result)
            }
        }
    }
    private fun onEmailChange(newEmail:String){
        loginState= loginState.copy(emailText=newEmail, emailErrorText = "")
    }
    private fun onPasswordChange(newPassword:String){
        loginState= loginState.copy(passwordText=newPassword, passwordErrorText = "")
    }

}