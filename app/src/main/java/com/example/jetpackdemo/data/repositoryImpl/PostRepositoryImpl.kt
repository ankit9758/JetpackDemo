package com.example.jetpackdemo.data.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.jetpackdemo.data.remote.posts.PostApi
import com.example.jetpackdemo.data.remote.posts.PostPagingSource
import com.example.jetpackdemo.domain.model.Post
import com.example.jetpackdemo.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val api: PostApi) :PostRepository{
    override fun getPosts(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = { PostPagingSource(api) }
        ).flow
    }
}