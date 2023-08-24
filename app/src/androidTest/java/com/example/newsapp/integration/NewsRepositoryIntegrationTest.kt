package com.example.newsapp.integration

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.dao.NewsDao
import com.example.newsapp.data.dao.SavedNewsDao
import com.example.newsapp.data.db.NewsDB
import com.example.newsapp.data.db.SavedNewsDB
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.integration.util.ArticlesLists
import com.example.newsapp.integration.util.SavedArticles
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltAndroidTest
class NewsRepositoryIntegrationTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    private lateinit var db: NewsDB
    private lateinit var savedNewsDB: SavedNewsDB
    private lateinit var newsDao: NewsDao
    private lateinit var savedNewsDao: SavedNewsDao
    private lateinit var apiService: ApiService
    private lateinit var newsRepository: NewsRepository

    @Before
    fun setup() {
        hiltRule.inject()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, NewsDB::class.java).build()
        savedNewsDB = Room.inMemoryDatabaseBuilder(context, SavedNewsDB::class.java).build()
        newsDao = db.newsDao()
        savedNewsDao = savedNewsDB.savedNewsDao()
        apiService = Retrofit.Builder()
            .baseUrl("https://google.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
        newsRepository = NewsRepository(
            apiService,
            savedNewsDao,
            newsDao
        )
    }

    @Test
    fun givenEmptyDBWhenGetListThenItEmpty() {
        runTest {
            val actualArticles = newsRepository.getCachedArticles().first()
            assertThat(actualArticles).isEmpty()
        }
    }

    @Test
    fun givenListInDbWhenGetListThenItReturn() {
        runTest {
            newsDao.insertAllArticles(ArticlesLists.expectedArticleDboList)
            val actualArticles = newsRepository.getCachedArticles().first()
            assertThat(actualArticles).isEqualTo(ArticlesLists.expectedArticleDtoList)
        }
    }

    @Test
    fun givenListWhenSetListThenItReturnFromDB() {
        runTest {
            newsRepository.insertNewArticlesToDB(ArticlesLists.expectedArticleDtoList)
            val actualArticles = newsDao.getAllCachedArticles().first()
            assertThat(actualArticles).isEqualTo(ArticlesLists.expectedArticleDboList)
        }
    }

    @Test
    fun givenListInDbWhenDeleteItThenDBIsEmpty() {
        runTest {
            newsDao.insertAllArticles(ArticlesLists.expectedArticleDboList)

            newsRepository.deleteAllArticles()
            val actualArticles = newsDao.getAllCachedArticles().first()

            assertThat(actualArticles).isEmpty()
        }
    }

    @Test
    fun givenEmptySavedDBWhenSaveUrlThenGetInDBIt() {
        runTest {
            newsRepository.saveNews(SavedArticles.savedNewsDbo)

            val actualNews = savedNewsDao.getSavedNews(SavedArticles.savedNewsDbo.url)

            assertThat(actualNews).isEqualTo(SavedArticles.savedNewsDbo)
        }
    }

    @Test
    fun givenListInSavedDBWhenGetThenGetIt() {
        runTest {
            savedNewsDao.saveNews(SavedArticles.savedNewsDbo)

            val actualNews = newsRepository.getSavedNews(SavedArticles.savedNewsDbo.url)

            assertThat(actualNews).isEqualTo(SavedArticles.savedNewsDbo)
        }
    }

    @Test
    fun givenListInSavedDBWhenDeleteThenNoItIn() {
        runTest {
            savedNewsDao.saveNews(SavedArticles.savedNewsDbo)

            newsRepository.deleteNews(SavedArticles.savedNewsDbo.url)

            val actualNews = newsRepository.getSavedNews(SavedArticles.savedNewsDbo.url)

            assertThat(actualNews).isNull()
        }
    }

    @After
    fun tearDown() {
        db.close()
    }
}