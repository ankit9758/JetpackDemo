package com.example.jetpackdemo.di

import com.example.jetpackdemo.data.remote.posts.PostApi
import com.example.jetpackdemo.data.remote.posts.PostPagingSource
import com.example.jetpackdemo.data.repositoryImpl.PostRepositoryImpl
import com.example.jetpackdemo.domain.repository.PostRepository
import com.example.jetpackdemo.domain.usecase.GetPostsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PostModule {
    @Provides
    fun providePostPagingSource(api: PostApi): PostPagingSource {
        return PostPagingSource(api)
    }

    @Provides
    fun providePostRepository(postApi: PostApi): PostRepository {
        return PostRepositoryImpl(postApi)
    }
    @Provides
    fun providePostUseCase(postRepository: PostRepository): GetPostsUseCase {
        return GetPostsUseCase(postRepository)
    }
}