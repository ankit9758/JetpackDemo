package com.example.jetpackdemo.data.remote.posts

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jetpackdemo.data.mapper.toDomain
import com.example.jetpackdemo.domain.model.Post
import kotlinx.coroutines.delay
import javax.inject.Inject

class PostPagingSource @Inject constructor(private val postApi: PostApi) : PagingSource<Int, Post>() {
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val skip = params.key ?: 0
            delay(1500) //to show the loader
            val response = postApi.getPosts(limit = params.loadSize, skip = skip)
            LoadResult.Page(
                data = response.posts.map { it.toDomain() }, prevKey = if (skip == 0) null else skip - params.loadSize,
                nextKey = if (response.posts.isEmpty()) null else skip + params.loadSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}