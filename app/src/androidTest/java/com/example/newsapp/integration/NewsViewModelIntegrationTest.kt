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
import com.example.newsapp.domain.GetNewsResponseUseCase
import com.example.newsapp.domain.InsertCachedArticlesUseCase
import com.example.newsapp.ui.state.NewsListUIState
import com.example.newsapp.viewmodel.NewsListViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltAndroidTest
class NewsViewModelIntegrationTest {
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
    private lateinit var getNewsResponseUseCase: GetNewsResponseUseCase

    private lateinit var newsListViewModel: NewsListViewModel

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

        getNewsResponseUseCase = GetNewsResponseUseCase(newsRepository, ioDispatcher)
        getNewsFromDBUseCase = GetNewsFromDBUseCase(newsRepository, ioDispatcher)
        deleteNewsInDBUseCase = DeleteNewsInDBUseCase(newsRepository, ioDispatcher)
        insertCachedArticlesUseCase = InsertCachedArticlesUseCase(newsRepository, ioDispatcher)

        newsListViewModel = NewsListViewModel(
            getNewsResponseUseCase = getNewsResponseUseCase,
            getNewsFromDBUseCase = getNewsFromDBUseCase,
            insertCachedArticlesUseCase = insertCachedArticlesUseCase,
            deleteNewsInDBUseCase = deleteNewsInDBUseCase
        )
    }

    @Test
    fun givenViewModelWhenStartThenStateIsLoading() {
        runTest {
            assertThat(newsListViewModel.uiState.value).isEqualTo(NewsListUIState.LOADING)
        }
    }

    @Test
    fun givenViewModelWhenStartThenCheckAllStates() {
        runTest {
            val listState  = newsListViewModel.uiState.take(3).toList()
            assertThat(listState[0]).isEqualTo(NewsListUIState.LOADING)
            assertThat(listState[1]).isEqualTo(NewsListUIState.SUCCESS(articles = listOf()))
            assertThat(listState[2]).isEqualTo(NewsListUIState.ERRORFetch(articles = listOf(), message = "404 : "))
        }
    }

    @After
    fun tearDown() {
        db.close()
    }
}