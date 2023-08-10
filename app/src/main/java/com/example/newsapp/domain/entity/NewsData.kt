package com.example.newsapp.domain.entity

data class NewsData(
    val news: News,
    val errorMessage: String? = null
)
