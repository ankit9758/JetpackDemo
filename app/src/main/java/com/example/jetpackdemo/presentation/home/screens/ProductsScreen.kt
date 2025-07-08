package com.example.jetpackdemo.presentation.home.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackdemo.presentation.products.screens.ProductItemRow
import com.example.jetpackdemo.presentation.products.state.ProductUiState
import com.example.jetpackdemo.presentation.products.viewmodels.ProductViewModel
import com.example.jetpackdemo.utils.NoDataFoundScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(productViewModel: ProductViewModel = hiltViewModel()) {

    val uiState by productViewModel.uiState.collectAsState()

    /* first load only once */
    LaunchedEffect(Unit) { productViewModel.load() }

    /* ── 3. remember if the first load has completed ── */
    var firstLoadDone by remember { mutableStateOf(false) }
    LaunchedEffect(uiState) {
        if(!firstLoadDone){
            if (uiState !is ProductUiState.Loading && uiState !is ProductUiState.Idle) firstLoadDone = true
        }

    }

    /* ── 4. isRefreshing = only after first data arrives & VM says “Loading” ── */
    val isRefreshing = uiState is ProductUiState.Loading && firstLoadDone

    /* pull‑to‑refresh machinery */
    val pullRefreshState = rememberPullToRefreshState()
        PullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            isRefreshing = isRefreshing, onRefresh = {
              //  isRefreshing = true
                productViewModel.load()
              // refreshing = false
            }, state = pullRefreshState
        )
        {
            when (uiState) {
                /* 1️⃣ INITIAL load: show spinner only when list is still empty */
                is ProductUiState.Loading ->{
                    if (!firstLoadDone) {
                        Box(Modifier.fillMaxSize(), Alignment.Center) {
                            CircularProgressIndicator()
                    }
                }

                }
                /* 2️⃣ ERROR state */
                is ProductUiState.Error -> {
                    NoDataFoundScreen(
                        title = "Error: ${(uiState as ProductUiState.Error).message}",
                        titleDesc = "Seems Like There is something went wrong.",
                        onClick = {
                            productViewModel.load()
                        })
                }
                /* 3️⃣ DATA state */
                is ProductUiState.Success -> {
                    val list = (uiState as ProductUiState.Success).items
                    if (list.isEmpty()) {
                        NoDataFoundScreen(
                            title = "No Products Found",
                            titleDesc = "Seems Like There is no Data Available.",
                            onClick = {
                                productViewModel.load()
                            })
                    } else {

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(items = list, key = { it.id }) { product ->
                                ProductItemRow(
                                    product,
                                    onClick = {},
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }

                }

                else -> {}
            }

       }
}