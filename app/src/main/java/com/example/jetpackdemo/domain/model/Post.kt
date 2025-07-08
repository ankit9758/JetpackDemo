package com.example.jetpackdemo.domain.model

data class Post(
    val id: Int,
    val title: String,
    val body: String,
    val views: Int,
    val userId: Int
)
