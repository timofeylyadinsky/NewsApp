package com.example.newsapp

import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.dao.NewsDao
import com.example.newsapp.data.dao.SavedNewsDao
import com.example.newsapp.data.entity.SavedNewsDbo
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.domain.DeleteNewsUseCase
import com.example.newsapp.domain.IsNewsSavedUseCase
import com.example.newsapp.domain.SaveNewsUseCase
import com.example.newsapp.viewmodel.SavedNewsViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class SavedNewsViewModelUseCase {

    @MockK
    private lateinit var savedNewsDao: SavedNewsDao

    @MockK
    private lateinit var apiService: ApiService

    @MockK
    private lateinit var newsDao: NewsDao

    private lateinit var newsRepo: NewsRepository

    private lateinit var isNewsSavedUseCase: IsNewsSavedUseCase

    private lateinit var savedNewsViewModel: SavedNewsViewModel

    private lateinit var saveNewsUseCase: SaveNewsUseCase

    private lateinit var deleteNewsUseCase: DeleteNewsUseCase

    private val validTestUrl = "https://google.com"

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        savedNewsDao = mockk()
        newsDao = mockk()
        apiService = mockk()
        newsRepo =
            NewsRepository(savedNewsDao = savedNewsDao, newsDao = newsDao, apiService = apiService)
        isNewsSavedUseCase = IsNewsSavedUseCase(newsRepo, Dispatchers.IO)
        saveNewsUseCase = SaveNewsUseCase(newsRepo, Dispatchers.IO)
        deleteNewsUseCase = DeleteNewsUseCase(newsRepo, Dispatchers.IO)
        savedNewsViewModel = SavedNewsViewModel(
            isNewsSavedUseCase = isNewsSavedUseCase,
            saveNewsUseCase = saveNewsUseCase,
            deleteNewsUseCase = deleteNewsUseCase
        )
        coEvery {
            savedNewsDao.getSavedNews(validTestUrl)
        } returns SavedNewsDbo(id = 1, url = validTestUrl)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `test state of saved news`() {
        runTest {
            savedNewsViewModel.changeUrl(validTestUrl)
            assertThat(savedNewsViewModel.uiState.url).isEqualTo(validTestUrl)
            assertThat(savedNewsViewModel.uiState.isSaved).isEqualTo(true)
        }
    }

    @After
    fun tearDown() {
    }
}