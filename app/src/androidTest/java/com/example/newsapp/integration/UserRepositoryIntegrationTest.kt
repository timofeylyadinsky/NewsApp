package com.example.newsapp.integration

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.newsapp.data.dao.UserDao
import com.example.newsapp.data.db.UserDB
import com.example.newsapp.data.entity.User
import com.example.newsapp.data.repository.module.DatabaseModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class UserRepositoryIntegrationTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    lateinit var db: UserDB
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        hiltRule.inject()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, UserDB::class.java).build()
        userDao = db.userDao()
        //userDao.saveUser(User(isLocked = true, passcode = "1111"))
    }

    @Test
    fun daoTest() {
        Log.d("!!!!!!!!", userDao.getUser().toString())
    }

    @After
    fun tearDown() {
        db.close()
    }
}