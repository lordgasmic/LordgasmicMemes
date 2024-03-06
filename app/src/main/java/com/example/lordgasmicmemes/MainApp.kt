package com.example.lordgasmicmemes

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lordgasmicmemes.screens.LOGIN_ROUTE
import com.example.lordgasmicmemes.screens.LoginScreen
import com.example.lordgasmicmemes.screens.MEME_ROUTE
import com.example.lordgasmicmemes.screens.MemeScreen
import com.example.lordgasmicmemes.ui.theme.LordgasmicMemesTheme

class MainApp {

    @Composable
    fun MainApp() {
        LordgasmicMemesTheme {
            var navController = rememberNavController()
            val currentBackStack by navController.currentBackStackEntryAsState()
            val currentDestination = currentBackStack?.destination

            Scaffold(

            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = LOGIN_ROUTE,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(route = LOGIN_ROUTE) {
                        LoginScreen()
                    }
                    composable(route = MEME_ROUTE) {
                        MemeScreen()
                    }
                }
            }
        }
    }
}