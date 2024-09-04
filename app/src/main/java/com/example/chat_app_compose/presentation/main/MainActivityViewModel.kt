package com.example.chat_app_compose.presentation.main

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat_app_compose.data.local.LocalAuthData
import com.example.chat_app_compose.data.remote.AuthServices
import com.example.chat_app_compose.presentation.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.Route
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val authServices: AuthServices,
) : ViewModel() {
    var splashCondition = mutableStateOf(true)
        private set
    var startDestination:String by mutableStateOf(Routes.UserAuthGraph.route)
    fun changeStartDestination(route: String){
        if(route==Routes.UserLoggedInGraph.route||route==Routes.UserAuthGraph.route)
        {
            startDestination= route
        }
    }
    init {
        viewModelScope.launch {
            startDestination= if(authServices.getCurrentUser()!=null && authServices.isUserEmailVerified())
                {Routes.UserLoggedInGraph.route}
            else{
                Routes.UserAuthGraph.route
            }
            delay(500)
            splashCondition.value=false
        }
    }
}