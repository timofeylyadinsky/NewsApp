package com.example.newsapp.ui.state

import com.example.newsapp.domain.entity.Article

sealed class NewsListUIState {
    object LOADING : NewsListUIState()
    data class SUCCESS(val articles: List<Article>) : NewsListUIState()
    data class ERROR(val message: String) : NewsListUIState()
    data class ERRORFetch(val articles: List<Article>, val message: String) : NewsListUIState()
}
