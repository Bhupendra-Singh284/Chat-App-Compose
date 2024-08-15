package com.example.chatappcompose.data.user_logged_in_status

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.chatappcompose.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserManager {
    fun readUserEntry(context:Context):Flow<Boolean>
    {
        return context.datastore.data
            .map{ preferences ->
                preferences[userLogged] ?: false
            }
    }

    fun writeUserEntry(context: Context)
    {
        suspend fun incrementCounter() {
            context.datastore.edit { settings ->
                settings[userLogged]= true
            }
        }
    }
}

private val  userLogged = booleanPreferencesKey(Constants.userLoggedKey)
private val Context.datastore:DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(Constants.datastoreName)