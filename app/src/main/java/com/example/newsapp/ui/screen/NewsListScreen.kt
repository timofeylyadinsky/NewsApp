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
import com.example.newsapp.ui.state.NewsListUIState
import com.example.newsapp.viewmodel.NewsListViewModel

@Composable
fun NewsListScreen(
    newsListViewModel: NewsListViewModel = hiltViewModel()
) {
    val state by newsListViewModel.uiState.collectAsState()
    when (state) {
        NewsListUIState.LOADING -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is NewsListUIState.ERROR -> {
            Text(text = "failure ${(state as NewsListUIState.ERROR).message}")
        }

        is NewsListUIState.SUCCESS -> {
            val articles = (state as NewsListUIState.SUCCESS).articles
            Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
                articles.forEach {
                    //NewsItem(article = it)
                }
            }
        }
    }
}
