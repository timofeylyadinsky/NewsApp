package com.example.newsapp

import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.dao.NewsDao
import com.example.newsapp.data.dao.SavedNewsDao
import com.example.newsapp.data.entity.ArticleDbo
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.domain.DeleteNewsInDBUseCase
import com.example.newsapp.domain.GetNewsFromDBUseCase
import com.example.newsapp.domain.GetNewsResponseUseCase
import com.example.newsapp.domain.InsertCachedArticlesUseCase
import com.example.newsapp.domain.entity.Article
import com.example.newsapp.domain.entity.Source
import com.example.newsapp.ui.state.NewsListUIState
import com.example.newsapp.viewmodel.NewsListViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class NewsListViewModelUnitTest {
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

    private lateinit var newsListViewModel: NewsListViewModel
    private lateinit var getNewsResponseUseCase: GetNewsResponseUseCase
    private lateinit var insertCachedArticlesUseCase: InsertCachedArticlesUseCase
    private lateinit var deleteNewsInDBUseCase: DeleteNewsInDBUseCase

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        savedNewsDao = mockk()
        newsDao = mockk()
        apiService = mockk()
        newsRepo =
            NewsRepository(savedNewsDao = savedNewsDao, newsDao = newsDao, apiService = apiService)
        getNewsFromDBUseCase = GetNewsFromDBUseCase(newsRepo, Dispatchers.IO)
        getNewsResponseUseCase = GetNewsResponseUseCase(newsRepo, Dispatchers.IO)
        deleteNewsInDBUseCase = DeleteNewsInDBUseCase(newsRepo, Dispatchers.IO)
        insertCachedArticlesUseCase = InsertCachedArticlesUseCase(newsRepo, Dispatchers.IO)
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun `test receiving start loading message`() {
        runTest {
            newsListViewModel = NewsListViewModel(
                getNewsFromDBUseCase = getNewsFromDBUseCase,
                getNewsResponseUseCase = getNewsResponseUseCase,
                deleteNewsInDBUseCase = deleteNewsInDBUseCase,
                insertCachedArticlesUseCase = insertCachedArticlesUseCase
            )
            assertThat(newsListViewModel.uiState.value).isEqualTo(NewsListUIState.LOADING)
        }
    }

    @Test
    fun `Given view model and job When start job Then get loading and success message`() {
        runTest {
            coEvery {
                newsDao.getAllCachedArticles()
            } returns flow { actualArticleDboList }
            newsListViewModel = NewsListViewModel(
                getNewsFromDBUseCase = getNewsFromDBUseCase,
                getNewsResponseUseCase = getNewsResponseUseCase,
                deleteNewsInDBUseCase = deleteNewsInDBUseCase,
                insertCachedArticlesUseCase = insertCachedArticlesUseCase
            )

            //When launch state
            val job = launch {
                newsListViewModel.uiState.collect {}
            }

            //Then receive messages
            assertThat(newsListViewModel.uiState.value).isEqualTo(NewsListUIState.LOADING)
            assertThat(newsListViewModel.uiState.value).isEqualTo(
                NewsListUIState.SUCCESS(
                    expectedArticleList
                )
            )
            job.cancel()

        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}

@ExperimentalCoroutinesApi
class MainDispatcherRule(val dispatcher: TestDispatcher = StandardTestDispatcher()) :
    TestWatcher() {

    override fun starting(description: Description?) = Dispatchers.setMain(dispatcher)

    override fun finished(description: Description?) = Dispatchers.resetMain()

}