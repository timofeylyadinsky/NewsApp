package com.example.newsapp.integration

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.newsapp.data.dao.NewsDao
import com.example.newsapp.data.db.NewsDB
import com.example.newsapp.integration.util.ArticlesLists
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
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
            val expectedNews = ArticlesLists.expectedArticleDboList
            newsDao.insertAllArticles(articlesDbo = expectedNews)
            val actualNews = newsDao.getAllCachedArticles().first()
            assertThat(actualNews).isEqualTo(expectedNews)
        }
    }

    @Test
    fun givenNewsListWhenDeleteThenDBISEmpty() {
        runTest {
            newsDao.insertAllArticles(ArticlesLists.expectedArticleDboList)
            newsDao.deleteAllCachedNews()
            val actualNews = newsDao.getAllCachedArticles().first()
            assertThat(actualNews).isEmpty()
        }
    }


    @After
    fun tearDown() {
        db.close()
    }
}