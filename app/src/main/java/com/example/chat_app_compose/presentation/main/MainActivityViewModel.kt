package com.example.chat_app_compose.presentation.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat_app_compose.data.UserAuthHandler
import com.example.chat_app_compose.presentation.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userAuthHandler: UserAuthHandler
) : ViewModel() {
    var splashCondition = mutableStateOf(true)
        private set

    var startDestination = mutableStateOf(Routes.UserAuthGraph.route)
        private set
    private var email =""
    private var password=""

    fun getUserEmail():String{
        return email
    }

    fun getUserPassword():String{
        return password
    }

    init {
        viewModelScope.launch {
            userAuthHandler.readUserAuth().collect{
                value-> if(value!=""){
                    val content = value.split("/").toList()
                    email = content[0]
                    password=content[1]
                startDestination.value= Routes.UserChatGraph.route
            }
                delay(100)
                splashCondition.value=false
            }
        }
    }

}