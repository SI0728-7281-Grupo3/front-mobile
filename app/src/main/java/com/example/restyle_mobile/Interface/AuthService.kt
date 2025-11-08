package com.example.restyle_mobile.Interface

import com.example.restyle_mobile.Beans.SignInRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/v1/authentication/sign-up")
    suspend fun signUp(
        @Body credentials: SignUpRequest
    ): Response<AuthResponse>

    @POST("api/v1/authentication/sign-in")
    suspend fun signIn(
        @Body credentials: SignInRequest
    ): Response<AuthResponse>
}

data class SignUpRequest(val username: String, val password: String, val roles: List<String>)

data class AuthResponse(val token: String)