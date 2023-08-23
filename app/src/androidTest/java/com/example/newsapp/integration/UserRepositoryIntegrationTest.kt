package com.example.newsapp.integration

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.newsapp.data.dao.UserDao
import com.example.newsapp.data.db.UserDB
import com.example.newsapp.data.entity.User
import com.example.newsapp.data.repository.UserRepository
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class UserRepositoryIntegrationTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    private lateinit var db: UserDB
    private lateinit var userDao: UserDao
    private lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        hiltRule.inject()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, UserDB::class.java).build()
        userDao = db.userDao()
        userRepository = UserRepository(userDao)
    }

    @Test
    fun whenDBEmptyThenGetEmptyUserTest() {
        runTest {
            assertThat(userRepository.getUserInfo()).isNull()
        }
    }

    @Test
    fun givenUserWhenDBGetInfoThenGetThatUser() {
        runTest {
            val expectedUser = User(isLocked = true, passcode = "1111")
            userDao.saveUser(expectedUser)

            val actualUser = userRepository.getUserInfo()

            assertThat(actualUser).isEqualTo(expectedUser)
        }
    }


    @After
    fun tearDown() {
        db.close()
    }
}