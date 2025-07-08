package com.example.jetpackdemo.presentation.home.screens


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.jetpackdemo.domain.model.Post
import com.example.jetpackdemo.presentation.home.viewmodel.PostViewModel
import com.example.jetpackdemo.utils.NetworkConnectivityObserver
import com.example.jetpackdemo.utils.NoDataFoundScreen

@Composable
fun HomeScreen(viewModel: PostViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val networkObserver = remember { NetworkConnectivityObserver(context) }
    val isConnected by networkObserver.observe().collectAsState(initial = true)

    val posts = viewModel.posts.collectAsLazyPagingItems()


    val isRefreshing = posts.loadState.refresh is LoadState.Loading
    val isAppending = posts.loadState.append is LoadState.Loading
    val isError = posts.loadState.refresh is LoadState.Error
    LaunchedEffect(posts.loadState) {
        Log.d("PagingLoadState", "Append = ${posts.loadState.append}")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            !isConnected->{
                NoDataFoundScreen(title = "No Internet",
                    titleDesc = "Seems Like You are not connected to internet.",
                    onClick = {
                        posts.refresh()
                    })
            }
            isRefreshing-> {
                // 🔥 Initial full screen loader
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            isError -> {
                // ❌ Optional error message
                val error = (posts.loadState.refresh as? LoadState.Error)?.error
                Text(
                    text = "Error: ${error?.localizedMessage}",
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
            }
            posts.itemCount == 0 &&  posts.loadState.refresh !is LoadState.Loading &&
                    posts.loadState.refresh !is LoadState.Error -> {
                // ⚠️ No data on first page
               NoDataFoundScreen(title = "No Post Found",
                   titleDesc = "Seems Like There is no post Available.",
                   onClick = {
                       posts.refresh()
                   })
            }
            else -> {
                LazyColumn {
                    items(posts.itemCount) { index ->
                        posts[index]?.let { post ->
                            PostItem(post = post)
                        }
                    }

                    // 🔽 Footer loader
                    if (isAppending) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PostItem(post: Post) {
    post.let {
        Card(
            modifier = Modifier
                .fillMaxWidth() // 🔥 animation!
                .padding(16.dp, vertical = 8.dp)
                .clickable {  }, shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = it.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.size(15.dp))
                Text(text = it.body, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.size(15.dp))
                Text(text = "Views: ${it.views}", style = MaterialTheme.typography.titleMedium)
            }
        }

    }
}

