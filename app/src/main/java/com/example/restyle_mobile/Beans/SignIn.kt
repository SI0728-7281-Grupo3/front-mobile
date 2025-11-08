package com.example.restyle_mobile.Beans

data class SignInRequest (
    val username: String,
    val password: String
)

data class SignInResponse (
    val token: String
)