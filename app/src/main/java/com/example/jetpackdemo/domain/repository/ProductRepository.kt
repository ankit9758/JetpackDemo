package com.example.jetpackdemo.domain.repository

import com.example.jetpackdemo.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(): Result<List<Product>>
}