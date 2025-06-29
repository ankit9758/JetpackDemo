package com.example.jetpackdemo.domain.model

//User.kt - Domain Layer
data class User(
    val id: Int,
    val username: String,
    val email: String,
    val password: String
)