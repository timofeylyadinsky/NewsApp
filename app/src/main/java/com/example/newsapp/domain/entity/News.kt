package com.example.newsapp.domain.entity

import com.example.newsapp.data.entity.ArticleDto
import com.example.newsapp.data.entity.NewsDto
import com.example.newsapp.data.entity.SourceDto

data class News(
    val articles: List<Article>
)

data class Article(
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?
)

data class Source(
    val name: String?
)

fun NewsDto.toNews() = News(
    articles = articles.map { it.toArticle() }
)

fun ArticleDto.toArticle() = Article(
    source = source?.toSource(),
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt
)

fun SourceDto.toSource() = Source(name = name)