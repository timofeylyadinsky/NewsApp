package com.example.newsapp.data.entity

data class NewsDto(
    val articles: List<ArticleDto>
)

data class ArticleDto(
    val source: SourceDto?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?
)

data class SourceDto(
    val name: String?
)

fun ArticleDto.toArticleDbo() = ArticleDbo(
    source = source?.name,
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt
)