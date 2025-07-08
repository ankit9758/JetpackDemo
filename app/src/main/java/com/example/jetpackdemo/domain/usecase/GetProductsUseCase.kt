package com.example.jetpackdemo.domain.usecase

import com.example.jetpackdemo.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val productRepository: ProductRepository)  {
    suspend operator fun invoke() = productRepository.getProducts()
}