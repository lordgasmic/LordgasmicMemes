package com.example.lordgasmicmemes.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lordgasmicmemes.R
import com.example.lordgasmicmemes.model.LoginState
import com.example.lordgasmicmemes.model.LoginViewModel
import com.example.lordgasmicmemes.network.LoginRequest

object Login {
    val route= "login"
}
@Composable
fun LoginForm(
    onLoginButtonSuccess: () -> Unit = {}
){
    var username by remember { mutableStateOf("lordgasmic") }
    var password by remember { mutableStateOf("") }
    var token by remember { mutableStateOf("") }
    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.LoginViewModelFactory)

    Column {
        LoginField(rValue = R.string.username, tField = username, onValueChange = {username = it} )
        LoginField(rValue = R.string.password, password = true, tField = password, onValueChange = {password = it})
        Button(onClick = {callLogin(username,password, loginViewModel) }) {
            Text(stringResource(id = R.string.login))
        }
        TextField(value = token, onValueChange = {token = it})
    }

    when(loginViewModel.loginState) {
        is LoginState.Success -> {
            onLoginButtonSuccess()
        }
        is LoginState.Loading -> Log.w("memes", "loading")
        is LoginState.Error -> Log.w("memes", "error")
    }
}

fun callLogin(username: String, password: String, loginViewModel: LoginViewModel) {
    loginViewModel.doLogin(LoginRequest(username,password))
}

@Composable
fun LoginField(rValue: Int, password: Boolean = false,  tField:String, onValueChange: (String)->Unit, modifier: Modifier = Modifier){
    TextField(
        value = tField ,
        label = { Text(stringResource(id = rValue)) },
        onValueChange = onValueChange,
        modifier = modifier,
        visualTransformation = if (password) PasswordVisualTransformation() else VisualTransformation.None,
        singleLine = true
    )
}
