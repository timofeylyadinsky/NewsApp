package com.example.newsapp.integration

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.newsapp.data.dao.UserDao
import com.example.newsapp.data.db.UserDB
import com.example.newsapp.data.entity.User
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class UserDaoIntegrationTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    private lateinit var db: UserDB
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        hiltRule.inject()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, UserDB::class.java).build()
        userDao = db.userDao()
    }

    @Test
    fun givenNoUserWhenGetUserFromDbThenReturnEmpty() {
        assertThat(userDao.getUser()).isNull()
    }

    @Test
    fun givenUserInDBWhenGetUserFromDbThenReturnUser() {
        val expectedUser = User(isLocked = true, passcode = "1111")
        userDao.saveUser(expectedUser)
        val actualUser = userDao.getUser()
        assertThat(actualUser).isEqualTo(expectedUser)
    }

    @After
    fun tearDown() {
        db.close()
    }
}