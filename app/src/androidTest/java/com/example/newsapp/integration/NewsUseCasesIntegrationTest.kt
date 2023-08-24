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
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
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


    @After
    fun tearDown() {
        db.close()
    }
}