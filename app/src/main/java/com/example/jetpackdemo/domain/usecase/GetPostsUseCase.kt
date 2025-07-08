package com.example.jetpackdemo.domain.usecase

import androidx.paging.PagingData
import com.example.jetpackdemo.domain.model.Post
import com.example.jetpackdemo.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostsUseCase @Inject constructor( private val repository: PostRepository) {
    operator fun invoke(): Flow<PagingData<Post>> {
        return repository.getPosts()
    }}