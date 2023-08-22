package com.example.newsapp

import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.dao.NewsDao
import com.example.newsapp.data.dao.SavedNewsDao
import com.example.newsapp.data.entity.SavedNewsDbo
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.domain.IsNewsSavedUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class SavedNewsUseCaseUnitTest {
    @MockK
    private lateinit var savedNewsDao: SavedNewsDao

    @MockK
    private lateinit var apiService: ApiService

    @MockK
    private lateinit var newsDao: NewsDao

    private lateinit var newsRepo: NewsRepository

    private lateinit var isNewsSavedUseCase: IsNewsSavedUseCase

    private val validTestUrl = "https://google.com"

    private var notValidTestUrl = ""

    @Before
    fun setup() {
        savedNewsDao = mockk()
        newsDao = mockk()
        apiService = mockk()
        newsRepo =
            NewsRepository(savedNewsDao = savedNewsDao, newsDao = newsDao, apiService = apiService)
        isNewsSavedUseCase = IsNewsSavedUseCase(newsRepo, Dispatchers.IO)
        coEvery {
            savedNewsDao.getSavedNews(validTestUrl)
        } returns SavedNewsDbo(id = 1, url = validTestUrl)
    }

    @Test
    fun `Given not valid url When check Then receive news not saved`() {
        runTest {
            coEvery {
                savedNewsDao.getSavedNews(notValidTestUrl)
            } returns null
            assertThat(isNewsSavedUseCase(notValidTestUrl)).isFalse()
        }
    }

    @Test
    fun `Given valid url When check Received true news`() {
        runTest {
            assertThat(isNewsSavedUseCase(validTestUrl)).isTrue()
        }
    }

    @After
    fun tearDown() {
    }
}