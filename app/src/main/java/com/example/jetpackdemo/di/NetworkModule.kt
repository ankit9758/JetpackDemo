package com.example.jetpackdemo.di

import com.example.jetpackdemo.data.remote.ProductApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com")   // sample
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides fun provideProductApi(retrofit: Retrofit): ProductApi =
        retrofit.create(ProductApi::class.java)

}