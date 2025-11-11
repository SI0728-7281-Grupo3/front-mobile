package com.example.restyle_mobile.Interface

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestyleApiClient {
    private const val BASE_URL = "https://restyle-platform-bed4c3b3f3eug0ak.canadacentral-01.azurewebsites.net/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }
}
