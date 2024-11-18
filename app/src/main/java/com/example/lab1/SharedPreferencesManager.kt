package com.example.lab1

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {

    companion object {
        private const val PREFERENCES_NAME = "app_preferences"
        private const val NOTIFICATIONS_KEY = "notifications_enabled"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun areNotificationsEnabled(): Boolean {
        return sharedPreferences.getBoolean(NOTIFICATIONS_KEY, true)
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        sharedPreferences.edit().putBoolean(NOTIFICATIONS_KEY, enabled).apply()
    }
}
