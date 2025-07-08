package com.example.jetpackdemo.di

import com.example.jetpackdemo.data.remote.products.ProductApi
import com.example.jetpackdemo.data.remote.products.ProductRemoteDataSource
import com.example.jetpackdemo.data.remote.products.ProductRemoteDataSourceImpl
import com.example.jetpackdemo.data.repositoryImpl.ProductRepositoryImpl
import com.example.jetpackdemo.domain.repository.ProductRepository
import com.example.jetpackdemo.domain.usecase.GetProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object ProductModule {

    @Provides
    fun provideProductRemoteDataSource(api: ProductApi): ProductRemoteDataSource {
    return ProductRemoteDataSourceImpl(api)
    }

    @Provides
    fun provideProductRemoteRepository(productRemoteDataSource: ProductRemoteDataSource): ProductRepository {
        return ProductRepositoryImpl(productRemoteDataSource)
    }
    @Provides
    fun provideProductUseCase(productRepository: ProductRepository): GetProductsUseCase {
        return GetProductsUseCase(productRepository)
    }

}