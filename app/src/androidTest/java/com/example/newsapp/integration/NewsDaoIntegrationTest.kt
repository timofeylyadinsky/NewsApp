package com.example.newsapp.integration

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.newsapp.data.dao.NewsDao
import com.example.newsapp.data.db.NewsDB
import com.example.newsapp.data.entity.ArticleDbo
import com.example.newsapp.data.entity.ArticleDto
import com.example.newsapp.data.entity.SourceDto
import com.example.newsapp.domain.entity.Article
import com.example.newsapp.domain.entity.Source
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NewsDaoIntegrationTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    private lateinit var db: NewsDB
    private lateinit var newsDao: NewsDao

    @Before
    fun setup() {
        hiltRule.inject()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NewsDB::class.java).build()
        newsDao = db.newsDao()
    }

    @Test
    fun givenNewsListWhenSaveThenGetInDBIt() {
        runTest {
            val expectedNews = actualArticleDboList
            newsDao.insertAllArticles(articlesDbo = expectedNews)
            val actualNews = newsDao.getAllCachedArticles().first()
            assertThat(actualNews).isEqualTo(expectedNews)
        }
    }

    @Test
    fun givenNewsListWhenDeleteThenDBISEmpty() {
        runTest {
            newsDao.insertAllArticles(actualArticleDboList)
            newsDao.deleteAllCachedNews()
            val actualNews = newsDao.getAllCachedArticles().first()
            assertThat(actualNews).isEmpty()
        }
    }


    @After
    fun tearDown() {
        db.close()
    }

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
}