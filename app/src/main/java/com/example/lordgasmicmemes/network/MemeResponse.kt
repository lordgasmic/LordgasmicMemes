package com.example.lordgasmicmemes.network

data class MemeResponse(
    val name:String,
    val tags: List<String>,
    val thumbnailUrl: String,
    val url : String
)
