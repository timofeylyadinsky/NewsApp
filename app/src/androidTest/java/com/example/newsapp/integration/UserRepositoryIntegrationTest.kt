package com.example.newsapp.integration

import com.example.newsapp.data.dao.UserDao
import com.example.newsapp.data.db.UserDB
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
@UninstallModules(DatabaseModule::class)
class UserRepositoryIntegrationTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var db: UserDB
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        hiltRule.inject()
        userDao = db.userDao()
    }

    @Test
    fun daoTest() {
        print(userDao.getUser())
    }

    @After
    fun tearDown() {
        db.close()
    }
}