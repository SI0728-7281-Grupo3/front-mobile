package com.example.restyle_mobile.Beans

data class SignUpResponse(
    val id: Int?,
    val username: String?,
    val roles: List<String>?,
    val email: String?,
    val firstName: String?,
    val paternalSurname: String?,
    val maternalSurname: String?,
    val description: String?,
    val phone: String?,
    val image: String?
)
