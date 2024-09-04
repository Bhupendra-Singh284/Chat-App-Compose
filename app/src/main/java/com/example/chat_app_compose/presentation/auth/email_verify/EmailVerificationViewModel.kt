package com.example.chat_app_compose.presentation.auth.email_verify

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat_app_compose.data.remote.AuthServices
import com.example.chat_app_compose.util.MyResult
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailVerificationViewModel @Inject constructor(
    private  val authServices: AuthServices
):ViewModel() {

    var verificationLinkSent by mutableStateOf(false)
     fun getUserEmail():String
    {
         return authServices.getUserEmail()
    }

    var isUserVerified=false
        private  set
    var resultState: MyResult? by mutableStateOf(null)
        private set
    fun sendVerificationLink(){
        resultState=MyResult.Loading
        viewModelScope.launch {
            val result= authServices.sendEmailVerificationMail()
            resultState = if(result!=null) {
                MyResult.Failure(result)
            } else{
                if(!verificationLinkSent)
                {
                    verificationLinkSent=true
                }
                MyResult.Success
            }
        }
    }

    fun userContinue(){
        resultState=MyResult.Loading
        viewModelScope.launch{
            val result= authServices.isUserEmailVerified()
            resultState=if(result)
            {
                isUserVerified=true
                MyResult.Success
            }else{
                MyResult.Failure("Please verify your email first")
            }
        }
    }

    fun resetState(state:MyResult?)
    {
        resultState=state
    }
}