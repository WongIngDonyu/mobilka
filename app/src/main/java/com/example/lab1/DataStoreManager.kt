package com.example.lab1

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences") //делегат для DataStore и экземпляр DataStore. Данные хранятся ввиде preference типа ключ значение

class DataStoreManager(private val context: Context) {

    companion object {
        private val THEME_KEY = booleanPreferencesKey("theme_key")
    }

    suspend fun saveTheme(isDarkTheme: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkTheme
        }
    }

    val isDarkThemeFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[THEME_KEY] ?: false
    }
}
