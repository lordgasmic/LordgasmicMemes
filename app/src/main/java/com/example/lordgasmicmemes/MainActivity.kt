package com.example.lordgasmicmemes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lordgasmicmemes.screens.Login
import com.example.lordgasmicmemes.screens.LoginForm
import com.example.lordgasmicmemes.screens.Memes
import com.example.lordgasmicmemes.screens.MemesScreen
import com.example.lordgasmicmemes.ui.theme.LordgasmicMemesTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           LordgasmicApp()
        }
    }

}

@Composable
fun LordgasmicApp() {
    LordgasmicMemesTheme {
        val navController = rememberNavController()
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(navController = navController, startDestination = Login.route){
                composable(route = Login.route) {
                    LoginForm(
                        onLoginButtonSuccess = {navController.navigate(Memes.route)}
                    )
                }
                composable(route = Memes.route) {
                    MemesScreen()
                }
            }
        }
    }
}