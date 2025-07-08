package com.example.jetpackdemo.data.remote.posts

import com.example.jetpackdemo.data.model.PostResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {

    @GET("posts")
    suspend fun getPosts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): PostResponseDto
}