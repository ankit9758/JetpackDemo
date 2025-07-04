package com.example.jetpackdemo.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.jetpackdemo.domain.model.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = AppConstants.USER_PREFS)

class UserPreferences (private val context: Context) {

    companion object {
        val KEY_LOGGED_IN = booleanPreferencesKey(AppConstants.LOGGED_IN)
        val USER_PROFILE_KEY = stringPreferencesKey(AppConstants.USER_PROFILE_KEY)
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[KEY_LOGGED_IN] ?: false
    }

    suspend fun setLoggedIn(value: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[KEY_LOGGED_IN] = value
        }
    }


    fun getProfile(): Flow<User?> {
        return context.dataStore.data.map { prefs ->
            prefs[USER_PROFILE_KEY]?.let {
                Gson().fromJson(it, User::class.java)
            }
        }
    }

    suspend fun saveProfile(profile: User) {
        val json = Gson().toJson(profile)
        context.dataStore.edit { prefs ->
            prefs[USER_PROFILE_KEY] = json
        }
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }
}