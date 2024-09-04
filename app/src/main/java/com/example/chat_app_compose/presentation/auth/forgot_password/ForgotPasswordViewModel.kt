package com.example.chat_app_compose.presentation.auth.forgot_password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat_app_compose.data.remote.AuthServices
import com.example.chat_app_compose.presentation.auth.common.validators.emailValidator
import com.example.chat_app_compose.util.MyResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authServices: AuthServices
):ViewModel() {
    var forgotPasswordState by mutableStateOf(ForgotPasswordState())
        private set

    var resultState by mutableStateOf<MyResult?>(null)

    fun resetAuthState(state:MyResult?){
        resultState=state
    }

    fun onEvent(event: ForgotPasswordEvent){
        when(event)
        {
            is ForgotPasswordEvent.OnEmailChange->{
                emailChange(newEmail=event.newEmail)
            }
            is ForgotPasswordEvent.SendCode->{
                sendCode()
            }
        }
    }

    private fun sendCode(){
        val emailValidationResult = emailValidator(forgotPasswordState.email)
        if(emailValidationResult!="")
        {
            forgotPasswordState= forgotPasswordState.copy(emailErrorText = emailValidationResult)
            return
        }
        resultState=MyResult.Loading
        viewModelScope.launch {
           val result= authServices.sendPasswordResetEmail(forgotPasswordState.email)
            resultState = if(result!=null) {
                MyResult.Failure(result)
            }else{
                MyResult.Success
            }
        }
    }

    private fun emailChange(newEmail: String) {
        forgotPasswordState=forgotPasswordState.copy(email = newEmail)
    }
}