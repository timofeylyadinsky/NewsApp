package com.example.newsapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsapp.R
import com.example.newsapp.domain.entity.Article
import com.example.newsapp.ui.navigation.Screen
import com.example.newsapp.ui.state.NewsListUIState
import com.example.newsapp.viewmodel.NewsListViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun NewsListScreen(
    newsListViewModel: NewsListViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by newsListViewModel.uiState.collectAsState()
    when (state) {
        NewsListUIState.LOADING -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is NewsListUIState.ERROR -> {
            Text(
                text = "failure ${(state as NewsListUIState.ERROR).message}",
                style = TextStyle(
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        is NewsListUIState.ERRORFetch -> {
            val articles = (state as NewsListUIState.ERRORFetch).articles
            val errorMessage = (state as NewsListUIState.ERRORFetch).message
            Column {
                Text(
                    text = errorMessage,
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.error
                    )
                )
                NewsListColumn(articles = articles, navController = navController)
            }
        }

        is NewsListUIState.SUCCESS -> {
            val articles = (state as NewsListUIState.SUCCESS).articles
            NewsListColumn(articles = articles, navController = navController)
        }
    }
}

@Composable
fun NewsListColumn(
    articles: List<Article>,
    navController: NavController
) {
    Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
        articles.forEach {
            NewsItem(article = it, navController = navController)
        }
    }
}

@Composable
fun NewsItem(
    article: Article,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .fillMaxHeight(0.2f)
            .clickable(onClick = {
                navController.navigate(
                    Screen.NewsDetailsScreen.route + "/${
                        URLEncoder.encode(
                            article.url,
                            StandardCharsets.UTF_8.toString()
                        )
                    }"
                )
            }),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(10.dp)
        ) {
            GlideImage(
                imageModel = {
                    article.urlToImage
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.8f),
                imageOptions = ImageOptions(contentScale = ContentScale.Fit),
                previewPlaceholder = R.drawable.ic_launcher_background
            )
            Text(
                text = article.title.toString(),
                modifier = Modifier.padding(top = 5.dp, bottom = 15.dp),
                style = TextStyle(
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.W600,
                    fontStyle = FontStyle.Italic
                )
            )
            Text(
                text = article.source?.name.toString(),
                modifier = Modifier.padding(top = 0.dp, bottom = 5.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.W300
                )
            )
        }
    }
}