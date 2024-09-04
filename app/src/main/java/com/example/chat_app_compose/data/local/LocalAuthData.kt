package com.example.chat_app_compose.data.local

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.chat_app_compose.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


//its of no use, firebase automatically saves user credentials , this file is just for future use



class LocalAuthData @Inject constructor (
    private val application: Application) {
    fun readUserAuth():Flow<String>
    {
        return application.datastore.data
            .map{ preferences ->
                preferences[userAuthDetailsKey] ?: ""
            }
    }

    suspend fun saveUserAuth(email:String, password:String)
    {
        application.datastore.edit { settings ->
            settings[userAuthDetailsKey] = "$email/$password"
        }
    }
}

private val  userAuthDetailsKey = stringPreferencesKey(Constants.userAuthDetailsKey)
private val Context.datastore:DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(Constants.datastoreName)