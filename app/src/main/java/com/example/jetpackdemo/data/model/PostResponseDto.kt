package com.example.jetpackdemo.data.model

data class PostResponseDto(
    val posts: List<PostDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
data class PostDto(
    val id: Int,
    val title: String,
    val body: String,
    val views: Int,
    val userId: Int
)
