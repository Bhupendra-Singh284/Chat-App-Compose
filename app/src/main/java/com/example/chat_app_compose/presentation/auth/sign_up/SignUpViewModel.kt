package com.example.chat_app_compose.presentation.auth.sign_up

import android.net.Uri
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
import com.example.chat_app_compose.presentation.auth.common.validators.userNameValidator
import com.example.chat_app_compose.util.MyResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authServices: AuthServices
):ViewModel() {
     var signUpState by mutableStateOf(SignUpState())
         private set

    var resultState: MyResult? by mutableStateOf(null)
        private set

    var isUserVerified=false
        private set

    init {
        Log.d("MainActivity","View model created")
    }

    fun onSignUpEvent(event: SignUpEvent){
        when(event){
            is SignUpEvent.OnSignUp ->{
                onSignUp()
            }
            is SignUpEvent.UserImageChange->{
                onUserImageChange(event.newImage)
            }
            is SignUpEvent.OnGoogleSignUp->{
                onGoogleSignUp(event.credential)
            }
            is SignUpEvent.UserNameValueChange->{
                onUserNameChange(newUserName=event.newUserName)
            }
            is SignUpEvent.EmailValueChange->{
                onEmailChange(newEmail=event.newEmail)
            }
            is SignUpEvent.PasswordValueChange->{
                onPasswordChange(newPassword=event.newPassword)
            }
        }
    }

    fun resetAuthState(state:MyResult?) {
        resultState=state
    }

    private fun onUserImageChange(newImageUri: Uri) {
        signUpState = signUpState.copy(imageUri = newImageUri)
    }
    private fun onUserNameChange(newUserName: String) {
        signUpState= signUpState.copy(userName = newUserName)
    }
    private fun onEmailChange(newEmail: String) {
        signUpState= signUpState.copy(email = newEmail)
    }
    private fun onPasswordChange(newPassword: String) {
        signUpState = signUpState.copy(password = newPassword)
    }
    private fun onSignUp() {

        Log.d("MainActivity","On sign up started, resultState->${resultState.toString()}")
        val userNameValidationResult= userNameValidator(signUpState.userName)

        if(userNameValidationResult!=""){
            signUpState = signUpState.copy(userNameErrorText = userNameValidationResult)
            return
        }

        if(signUpState.userNameErrorText!=""){
            signUpState=signUpState.copy(userNameErrorText = "")
        }

        val emailValidationResult= emailValidator(signUpState.email)

        if(emailValidationResult!=""){
            signUpState = signUpState.copy(emailErrorText = emailValidationResult)
            return;
        }

        if(signUpState.emailErrorText!=""){
            signUpState=signUpState.copy(emailErrorText = "")
        }

        val passwordValidationResult= passwordValidator(signUpState.password)

        if(passwordValidationResult!=""){
            signUpState = signUpState.copy(passwordErrorText=passwordValidationResult)
            return
        }

        if(signUpState.passwordErrorText!=""){
            signUpState=signUpState.copy(passwordErrorText =  "")
        }


        resultState=MyResult.Loading
        viewModelScope.launch {
            Log.d("MainActivity","sign up launch start, resultState->${resultState.toString()}, Thread-> ${Thread.currentThread().name}, id-> ${Thread.currentThread().id}")
                val result =  authServices.createNewUser(signUpState.userName,signUpState.email,signUpState.password,signUpState.imageUri)
                Log.d("MainActivity","sign up launch after result, resultState->${resultState.toString()}, Thread-> ${Thread.currentThread().name}, id-> ${Thread.currentThread().id}")
                resultState = if(result==null) {
                    Log.d("MainActivity","sign up launch inside success, resultState->${resultState.toString()}, Thread-> ${Thread.currentThread().name}, id-> ${Thread.currentThread().id}")
                    MyResult.Success
                } else{
                    MyResult.Failure(result)
                }
            Log.d("MainActivity","sign up launch end, resultState->${resultState.toString()}, Thread-> ${Thread.currentThread().name}, id-> ${Thread.currentThread().id}")
        }

        Log.d("MainActivity","On sign up ended, resultState->${resultState.toString()}")
        //sign up flow
    }
    private fun onGoogleSignUp(credential: Credential) {
        resultState=MyResult.Loading
        viewModelScope.launch{
            val result=authServices.signUpWithGoogle(credential)
            resultState=if(result!=null)
            {
                MyResult.Failure(result)
            }else{
                isUserVerified=true
                MyResult.Success
            }
        }
    }


}