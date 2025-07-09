package com.example.jetpackdemo.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProductRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PostRetrofit

// for ❌ CoroutineDispatcher cannot be provided without an @Provides-annotated method.
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher