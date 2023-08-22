package com.example.newsapp

import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.dao.NewsDao
import com.example.newsapp.data.dao.SavedNewsDao
import com.example.newsapp.data.entity.ArticleDbo
import com.example.newsapp.data.entity.ArticleDto
import com.example.newsapp.data.entity.SourceDto
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.domain.GetNewsFromDBUseCase
import com.example.newsapp.domain.entity.Article
import com.example.newsapp.domain.entity.Source
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class NewsListUseCaseUnitTest {
    @MockK
    private lateinit var savedNewsDao: SavedNewsDao

    @MockK
    private lateinit var apiService: ApiService

    @MockK
    private lateinit var newsDao: NewsDao

    private lateinit var newsRepo: NewsRepository

    private lateinit var getNewsFromDBUseCase: GetNewsFromDBUseCase

    private val expectedArticleList = listOf<Article>(
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

    private val actualArticleDboList = listOf<ArticleDbo>(
        ArticleDbo(
            source = "CNN",
            author = "John John",
            title = "Tesla Sued Over Fatal Car Crash",
            description = "Tesla Sued Over Fatal Car Crash",
            url = "https://google.com",
            urlToImage = null,
            publishedAt = "2023-23-23"
        )
    )

    private val actualArticleDtoList = listOf(
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

    @Before
    fun setup() {
        savedNewsDao = mockk()
        newsDao = mockk()
        apiService = mockk()
        newsRepo =
            NewsRepository(savedNewsDao = savedNewsDao, newsDao = newsDao, apiService = apiService)


    }

    @Test
    fun `Given use case class return from db When use case is calling Then articles returned`() {
        runTest {
            //Given use case class
            getNewsFromDBUseCase = GetNewsFromDBUseCase(newsRepo, Dispatchers.IO)
            coEvery {
                newsDao.getAllCachedArticles()
            } returns flow { emit(actualArticleDboList) }
            //Then received articles
            assertThat(getNewsFromDBUseCase().first()).isEqualTo(expectedArticleList)
        }
    }

    @After
    fun tearDown() {
    }
}