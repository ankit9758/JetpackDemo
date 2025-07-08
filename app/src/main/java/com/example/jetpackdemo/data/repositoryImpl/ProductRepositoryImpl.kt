package com.example.jetpackdemo.data.repositoryImpl

import com.example.jetpackdemo.data.mapper.toDomain
import com.example.jetpackdemo.data.remote.ProductRemoteDataSource
import com.example.jetpackdemo.domain.model.Product
import com.example.jetpackdemo.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor( private val productRemoteDataSource: ProductRemoteDataSource) :ProductRepository {

    override suspend fun getProducts(): Result<List<Product>> {
        return try {
            val response = productRemoteDataSource.fetchProducts()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.map { it.toDomain() })
                } else {
                    Result.failure(Exception("Empty body"))
                }
            } else {
                Result.failure(Exception("Error ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
