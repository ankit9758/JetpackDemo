package com.example.jetpackdemo.data.remote

import com.example.jetpackdemo.data.model.ProductDto
import retrofit2.Response

import retrofit2.http.GET

interface ProductApi {
    @GET("/products")
    suspend fun getProducts(): Response<List<ProductDto>>
}