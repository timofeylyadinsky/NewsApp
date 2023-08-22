package com.example.newsapp

import com.example.newsapp.data.api.ApiService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsApiServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService

    @Before
    fun setup() {
        //Start mock server to make response
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Test
    fun `test response received`() {
        runTest {
            enqueueMockResponse("news.json")

            val response = apiService.getNews().body()
            val request = mockWebServer.takeRequest()

            assertThat(response).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us")

        }
    }

    @Test
    fun `test articles received`() {
        runTest {
            enqueueMockResponse("news.json")

            val response = apiService.getNews().body()
            val articles = response?.articles
            val firstArticle = articles?.get(0)

            assertThat(articles?.size).isEqualTo(2)
            assertThat(firstArticle?.source?.name).isEqualTo("Business Insider")

        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private fun enqueueMockResponse(
        fileName: String
    ) {
        javaClass.classLoader?.let {
            val inputStream = it.getResourceAsStream(fileName)
            val source = inputStream.source().buffer()
            val mockResponse = MockResponse()
            mockResponse.setBody(source.readString(Charsets.UTF_8))
            mockWebServer.enqueue(mockResponse)
        }
    }
}