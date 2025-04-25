package com.sokhal.groceryapp.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun saveAuthToken(token: String) {
        with(sharedPreferences.edit()) {
            putString(KEY_AUTH_TOKEN, token)
            apply()
        }
    }

    fun getAuthToken(): String? {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null)
    }

    fun clearAuthToken() {
        with(sharedPreferences.edit()) {
            remove(KEY_AUTH_TOKEN)
            apply()
        }
    }

    companion object {
        private const val PREFERENCES_NAME = "grocery_app_preferences"
        private const val KEY_AUTH_TOKEN = "auth_token"
    }
}