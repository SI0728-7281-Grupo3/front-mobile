package com.example.restyle_mobile.Beans

data class User(
    val id: Int,
    val fullName: String,
    val email: String,
    val isRemodeler: Boolean,
    val photoUrl: String?
)