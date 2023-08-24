package com.example.newsapp.integration

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.newsapp.data.dao.NewsDao
import com.example.newsapp.data.db.NewsDB
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule

@HiltAndroidTest
class NewsRepositoryIntegrationTest {
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

    @After
    fun tearDown() {
        db.close()
    }


}