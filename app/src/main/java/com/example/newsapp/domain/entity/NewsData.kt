package com.example.newsapp.domain.entity

data class NewsData(
    val news: List<Article> = listOf(),
    val errorMessage: String? = null
)
