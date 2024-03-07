package com.example.lordgasmicmemes.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.lordgasmicmemes.network.MemeApi
import com.example.lordgasmicmemes.network.MemeResponse
import kotlinx.coroutines.launch

sealed interface MemeState{
    data class Success(val memes: List<MemeResponse>) : MemeState
    data object Empty : MemeState
    data object Loading : MemeState
    data object Error: MemeState
}

class MemeViewModel: ViewModel() {
    var memeState: MemeState by mutableStateOf(MemeState.Empty)

    fun getMemes(token: String, tag: String) {
        viewModelScope.launch {
            memeState = MemeState.Loading

            memeState =  try {
                MemeState.Success(MemeApi.retrofitService.getMemes(token, tag))
            }
           catch (e: Exception) {
               MemeState.Error
           }

        }
    }

    companion object {
        val MemeViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MemeViewModel()
            }
        }
    }
}