package com.example.jetpackdemo.domain.model

//User.kt - Domain Layer
data class User(
    val username: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val imageUrl: String
)