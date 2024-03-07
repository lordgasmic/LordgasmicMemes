package com.example.lordgasmicmemes.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.graphics.createBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.lordgasmicmemes.R
import com.example.lordgasmicmemes.model.LoginViewModel
import com.example.lordgasmicmemes.model.MemeState
import com.example.lordgasmicmemes.model.MemeViewModel
import com.example.lordgasmicmemes.network.BASE_URL
import com.example.lordgasmicmemes.network.MemeResponse

object Memes {
    val route = "memes"
}

@Composable
fun MemesScreen() {
    val memeViewModel: MemeViewModel = viewModel(factory = MemeViewModel.MemeViewModelFactory)
    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.LoginViewModelFactory)

    SearchBar(loginViewModel = loginViewModel, memeViewModel = memeViewModel)
    Memes(memeViewModel)
}

@Composable
fun Memes(memeViewModel: MemeViewModel){
    when (memeViewModel.memeState) {
        is MemeState.Success -> {
            MemesList((memeViewModel.memeState as MemeState.Success).memes)
        }
        is MemeState.Loading -> {
            MemesLoading()
        }
        else  -> {

        }
    }
}

@Composable
fun MemesLoading() {
    Column {
        Image(painter = painterResource(id = R.drawable.loading_icon), contentDescription = null)
    }
}

@Composable
fun MemesList(memes: List<MemeResponse>) {
    LazyColumn {
        items(memes) {meme ->
            AsyncImage(
                model = BASE_URL + meme.url,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun MemeCard() {

}

@Composable
fun SearchBar(loginViewModel: LoginViewModel, memeViewModel: MemeViewModel) {
    var searchText by remember { mutableStateOf("") }
    val token: String = loginViewModel.settingsPreferences.collectAsState().value.authToken

    Column {
        Row {
            TextField(value = searchText, onValueChange = { searchText = it }, singleLine = true)
            Button(onClick = {call(searchText, token,  memeViewModel) }) {
                Text(stringResource(id = R.string.go))
            }

        }

    }
}

fun call(tag: String, token: String, viewModel: MemeViewModel){
    viewModel.getMemes(token, tag)
}