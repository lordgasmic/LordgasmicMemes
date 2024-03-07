package com.example.lordgasmicmemes.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

const val BASE_URL = "https://lordgasmic.com"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface LoginService     {
    @POST("/api/v1/login")
    suspend fun login(@Body login: LoginRequest): LoginResponse
}

object LoginApi{
    val retrofitService: LoginService by lazy {
        retrofit.create(LoginService::class.java)
    }
}

interface MemeService {
    @GET("/api/v1/memes/tag/{tag}")
    suspend fun getMemes(@Header("X-Lordgasmic-Token") token: String, @Path("tag") tag: String): List<MemeResponse>
}

object MemeApi {
    val retrofitService: MemeService by lazy {
        retrofit.create(MemeService::class.java)
    }
}