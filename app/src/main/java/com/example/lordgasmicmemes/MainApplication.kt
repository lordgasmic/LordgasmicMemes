package com.example.lordgasmicmemes

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.lordgasmicmemes.repositories.LordgasmicSettingsRepository

private const val LORDGASMIC_SETTINGS = "lordgasmic"

private val Context.dataStore by preferencesDataStore(
    name = LORDGASMIC_SETTINGS
)

class MainApplication : Application() {
     lateinit var settingsRepository: LordgasmicSettingsRepository

    override fun onCreate() {
        super.onCreate()

        settingsRepository = LordgasmicSettingsRepository(dataStore)
    }
}