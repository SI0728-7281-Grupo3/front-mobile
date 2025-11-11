package com.example.restyle_mobile.Interface

import com.example.restyle_mobile.Beans.SignInRequest
import com.example.restyle_mobile.Beans.SignInResponse
import com.example.restyle_mobile.Beans.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("api/v1/authentication/sign-up")
    suspend fun signUp(
        @Body credentials: SignUpRequest
    ):  Response<Unit>

    @POST("api/v1/authentication/sign-in")
    suspend fun signIn(
        @Body credentials: SignInRequest
    ): Response<SignInResponse>
}