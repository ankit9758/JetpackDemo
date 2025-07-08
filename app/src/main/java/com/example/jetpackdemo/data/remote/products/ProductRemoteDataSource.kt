package com.example.jetpackdemo.data.remote.products

import com.example.jetpackdemo.data.model.ProductDto
import retrofit2.Response
import javax.inject.Inject

interface ProductRemoteDataSource {
    suspend fun fetchProducts(): Response<List<ProductDto>>
}
class ProductRemoteDataSourceImpl @Inject constructor(private val api: ProductApi) :
    ProductRemoteDataSource {
    override suspend fun fetchProducts(): Response<List<ProductDto>> {
        return api.getProducts()
    }
}