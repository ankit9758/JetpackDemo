package com.example.jetpackdemo.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = AppConstants.USER_PREFS)

class UserPreferences (private val context: Context) {

    companion object {
        val KEY_LOGGED_IN = booleanPreferencesKey(AppConstants.LOGGED_IN)
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[KEY_LOGGED_IN] ?: false
    }

    suspend fun setLoggedIn(value: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[KEY_LOGGED_IN] = value
        }
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}