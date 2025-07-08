package com.example.jetpackdemo.presentation.products.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackdemo.domain.usecase.GetProductsUseCase
import com.example.jetpackdemo.presentation.products.state.ProductUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor( private val getProducts: GetProductsUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Idle)
    val uiState: StateFlow<ProductUiState> = _uiState

    fun load() = viewModelScope.launch {
        _uiState.value = ProductUiState.Loading
        _uiState.value = getProducts().fold(
            onSuccess = { ProductUiState.Success(it) },
            onFailure = { ProductUiState.Error(it.message ?: "Unknown") }
        )
    }

}