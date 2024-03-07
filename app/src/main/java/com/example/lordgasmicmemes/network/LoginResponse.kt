package com.example.lordgasmicmemes.network

data class LoginResponse (
    val username: String,
    val roles: Int ,
    val credentialsValid :Boolean,
    val enabled : Boolean,
    val authToken: String
)