package com.example.lordgasmicmemes.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class SettingsPreferences(val authToken: String)

class LordgasmicSettingsRepository(private val dataStore: DataStore<Preferences>) {

    private object SettingsKeys {
        val AUTH_TOKEN = stringPreferencesKey("authToken")
    }

    val currentAuthToken: Flow<SettingsPreferences> = dataStore.data.map {
        it -> val authToken = it[SettingsKeys.AUTH_TOKEN]?: ""
        SettingsPreferences(authToken)
    }

    suspend fun updateAuthToken(authToken: String) {
        dataStore.edit { it -> it[SettingsKeys.AUTH_TOKEN] = authToken }
    }
}