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
import com.example.newsapp.domain.DeleteNewsInDBUseCase
import com.example.newsapp.domain.GetNewsFromDBUseCase
import com.example.newsapp.domain.InsertCachedArticlesUseCase
import com.example.newsapp.integration.util.ArticlesLists
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltAndroidTest
class NewsUseCasesIntegrationTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    private lateinit var db: NewsDB
    private lateinit var savedNewsDB: SavedNewsDB
    private lateinit var newsDao: NewsDao
    private lateinit var savedNewsDao: SavedNewsDao
    private lateinit var apiService: ApiService
    private lateinit var newsRepository: NewsRepository

    private lateinit var getNewsFromDBUseCase: GetNewsFromDBUseCase
    private lateinit var deleteNewsInDBUseCase: DeleteNewsInDBUseCase
    private lateinit var insertCachedArticlesUseCase: InsertCachedArticlesUseCase

    private var ioDispatcher: CoroutineDispatcher = Dispatchers.IO

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
    fun givenListInDBWhenGetItThenReturnMappedIt() {
        runTest {
            getNewsFromDBUseCase = GetNewsFromDBUseCase(newsRepository, ioDispatcher)
            newsDao.insertAllArticles(ArticlesLists.expectedArticleDboList)

            val actualArticles = getNewsFromDBUseCase().first()

            assertThat(actualArticles).isEqualTo(ArticlesLists.expectedArticleList)

        }
    }

    @Test
    fun givenEmptyDBWhenGetItThenReturnEmptyList() {
        runTest {
            getNewsFromDBUseCase = GetNewsFromDBUseCase(newsRepository, ioDispatcher)

            val actualArticles = getNewsFromDBUseCase().first()

            assertThat(actualArticles).isEmpty()

        }
    }

    @Test
    fun givenArticlesListWhenInsertItThenInDBIt() {
        runTest {
            insertCachedArticlesUseCase = InsertCachedArticlesUseCase(newsRepository, ioDispatcher)

            insertCachedArticlesUseCase(ArticlesLists.expectedArticleList)

            val actualArticles = newsDao.getAllCachedArticles().first()

            assertThat(actualArticles).isEqualTo(ArticlesLists.expectedArticleDboList)
        }
    }

    @Test
    fun givenArticlesInDBWhenDeleteItThenDBISEmpty() {
        runTest {
            deleteNewsInDBUseCase = DeleteNewsInDBUseCase(newsRepository, ioDispatcher)
            deleteNewsInDBUseCase()
            val actualArticles = newsDao.getAllCachedArticles().first()
            assertThat(actualArticles).isEmpty()
        }
    }

    @After
    fun tearDown() {
        db.close()
    }
}