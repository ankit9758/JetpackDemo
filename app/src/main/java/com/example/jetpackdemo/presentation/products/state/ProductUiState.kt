package com.example.jetpackdemo.presentation.products.state

import com.example.jetpackdemo.domain.model.Product

sealed class ProductUiState {
    object Idle : ProductUiState()
    object Loading : ProductUiState()
    data class Success(val items: List<Product>) : ProductUiState()
    data class Error(val message: String) : ProductUiState()
}