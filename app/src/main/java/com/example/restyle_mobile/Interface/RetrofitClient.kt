package com.example.restyle_mobile.Interface

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

const val BASE_URL = "https://api.imgur.com/3/"

interface ImgurApi {
    @Multipart
    @POST("image")
    fun uploadImage(
        @Header("Authorization") authHeader: String,
        @Part image: MultipartBody.Part
    ): Call<UploadResponse>
}

data class UploadResponse(
    val data: ImageData,
    val success: Boolean
)

data class ImageData(
    val link: String
)

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    fun uploadImage(authHeader: String, imagePart: MultipartBody.Part): Call<UploadResponse> {
        val api = getClient().create(ImgurApi::class.java)
        return api.uploadImage(authHeader, imagePart)
    }
}