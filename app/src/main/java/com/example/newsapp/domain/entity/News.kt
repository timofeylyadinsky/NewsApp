package com.example.newsapp.domain.entity

data class News(
    val articles: List<Article>
)

data class Article(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String
)

data class Source(
    val name: String
)