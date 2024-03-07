package com.example.lordgasmicmemes.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.lordgasmicmemes.MainApplication
import com.example.lordgasmicmemes.network.LoginRequest
import com.example.lordgasmicmemes.network.LoginApi
import com.example.lordgasmicmemes.repositories.LordgasmicSettingsRepository
import com.example.lordgasmicmemes.repositories.SettingsPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.Exception

sealed interface LoginState {
    object Success: LoginState
    object Loading: LoginState
    object Error: LoginState
}

class LoginViewModel(private val settingsRepository: LordgasmicSettingsRepository) : ViewModel() {
    var loginState: LoginState by mutableStateOf(LoginState.Loading)

    val settingsPreferences: StateFlow<SettingsPreferences> = settingsRepository.currentAuthToken.stateIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SettingsPreferences("")
)

    fun doLogin(login: LoginRequest) {
        viewModelScope.launch {
            loginState = LoginState.Loading
            loginState = try {
                val loginResponse = LoginApi.retrofitService.login(login)
                saveAuthToken(loginResponse.authToken)
                LoginState.Success
            }
            catch (e: Exception) {
                LoginState.Error
            }
        }
    }

    private fun saveAuthToken(authToken:String) {
        viewModelScope.launch {
            settingsRepository.updateAuthToken(authToken)
        }
    }

    companion object {
        val LoginViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MainApplication)
                LoginViewModel(application.settingsRepository)
            }
        }
    }
}


