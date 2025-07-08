package com.example.jetpackdemo.di

import com.example.jetpackdemo.data.remote.products.ProductApi
import com.example.jetpackdemo.data.remote.posts.PostApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // Gson provider
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    // Logging Interceptor
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    // OkHttpClient
    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @ProductRetrofit
    @Provides
    @Singleton
    fun provideProductRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @PostRetrofit
    @Provides
    @Singleton
    fun providePostRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .client(client)// sample
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @Provides
    @Singleton
    fun provideProductApi(@ProductRetrofit retrofit: Retrofit): ProductApi =
        retrofit.create(ProductApi::class.java)

    @Provides
    @Singleton
    fun providePostApi(@PostRetrofit retrofit: Retrofit): PostApi =
        retrofit.create(PostApi::class.java)

}