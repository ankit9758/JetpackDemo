package com.example.jetpackdemo.domain.repository

import androidx.paging.PagingData
import com.example.jetpackdemo.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<PagingData<Post>>
}