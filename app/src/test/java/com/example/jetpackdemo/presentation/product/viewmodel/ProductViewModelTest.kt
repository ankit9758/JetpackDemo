package com.example.jetpackdemo.presentation.product.viewmodel

import com.example.jetpackdemo.domain.model.Product
import com.example.jetpackdemo.domain.usecase.GetProductsUseCase
import com.example.jetpackdemo.presentation.products.state.ProductUiState
import com.example.jetpackdemo.presentation.products.viewmodels.ProductViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModelTest {

    // Rule to swap the main dispatcher with a TestDispatcher
    private val testDispatcher = StandardTestDispatcher() // Runs tasks when advanceUntilIdle or runCurrent is called

    private lateinit var getProductsUseCase: GetProductsUseCase
    private lateinit var viewModel: ProductViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher) // Set the main dispatcher for coroutines
        getProductsUseCase = mockk() // Create a mock instance of ProductRepository
        viewModel = ProductViewModel(getProductsUseCase)
    }


    @Test
    fun `load emits Loading then Success`() = runTest {
        // Arrange
        val products = listOf(
            Product(1, "Product 1", 99.0, "url1"),
            Product(2, "Product 2", 199.0, "url2")
        )
        coEvery { getProductsUseCase() } returns Result.success(products)

        // Act
        viewModel.load()
        advanceUntilIdle()

        // Assert
        assertEquals(ProductUiState.Success(products), viewModel.uiState.value)
    }

    @Test
    fun `load emits Loading then Error on failure`() = runTest {
        // Arrange
        coEvery { getProductsUseCase() } returns Result.failure(Exception("Failed to fetch"))

        // Act
        viewModel.load()
        advanceUntilIdle()

        // Assert
        assertEquals(ProductUiState.Error("Failed to fetch"), viewModel.uiState.value)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher to the original one
    }
    @Test
    fun `initial state is Idle`() {
        assertEquals(ProductUiState.Idle, viewModel.uiState.value)
    }
}