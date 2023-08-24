package com.example.newsapp.integration.util

import com.example.newsapp.data.entity.ArticleDbo
import com.example.newsapp.data.entity.ArticleDto
import com.example.newsapp.data.entity.SourceDto
import com.example.newsapp.domain.entity.Article
import com.example.newsapp.domain.entity.Source

object ArticlesLists {
    val expectedArticleList = listOf<Article>(
        Article(
            source = Source("CNN"),
            author = "John John",
            title = "Tesla Sued Over Fatal Car Crash",
            description = "Tesla Sued Over Fatal Car Crash",
            url = "https://google.com",
            urlToImage = null,
            publishedAt = "2023-23-23"
        )
    )

    val expectedArticleDboList = listOf<ArticleDbo>(
        ArticleDbo(
            id = 1,
            source = "CNN",
            author = "John John",
            title = "Tesla Sued Over Fatal Car Crash",
            description = "Tesla Sued Over Fatal Car Crash",
            url = "https://google.com",
            urlToImage = null,
            publishedAt = "2023-23-23"
        )
    )

    val expectedArticleDtoList = listOf(
        ArticleDto(
            source = SourceDto(name = "CNN"),
            author = "John John",
            title = "Tesla Sued Over Fatal Car Crash",
            description = "Tesla Sued Over Fatal Car Crash",
            url = "https://google.com",
            urlToImage = null,
            publishedAt = "2023-23-23"
        )
    )
}