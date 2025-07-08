package com.example.jetpackdemo.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProductRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PostRetrofit