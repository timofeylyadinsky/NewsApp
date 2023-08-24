package com.example.newsapp.integration

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.newsapp.data.dao.UserDao
import com.example.newsapp.data.db.UserDB
import com.example.newsapp.data.entity.User
import com.example.newsapp.data.repository.UserRepository
import com.example.newsapp.domain.IsPasscodeCorrectUseCase
import com.example.newsapp.domain.IsPasscodeRequiredUseCase
import com.example.newsapp.domain.IsPasscodeSkipUseCase
import com.example.newsapp.domain.SavePasscodeUseCase
import com.example.newsapp.domain.SkipPasscodeUseCase
import com.example.newsapp.viewmodel.PasscodeViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class UserViewModelIntegrationTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    private lateinit var db: UserDB
    private lateinit var userDao: UserDao
    private lateinit var userRepository: UserRepository
    private lateinit var ioDispatcher: CoroutineDispatcher

    private lateinit var isPasscodeRequiredUseCase: IsPasscodeRequiredUseCase
    private lateinit var savePasscodeUseCase: SavePasscodeUseCase
    private lateinit var skipPasscodeUseCase: SkipPasscodeUseCase
    private lateinit var isPasscodeSkipUseCase: IsPasscodeSkipUseCase
    private lateinit var isPasscodeCorrectUseCase: IsPasscodeCorrectUseCase

    private lateinit var passcodeViewModel: PasscodeViewModel

    private val testPasscode = "1234"
    private val lockedUser = User(isLocked = true, passcode = testPasscode)
    private val notLockedUser = User(isLocked = false, passcode = "")

    @Before
    fun setup() {
        hiltRule.inject()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, UserDB::class.java).build()
        userDao = db.userDao()
        userRepository = UserRepository(userDao)
        ioDispatcher = Dispatchers.IO
        isPasscodeRequiredUseCase = IsPasscodeRequiredUseCase(userRepository, ioDispatcher)
        savePasscodeUseCase = SavePasscodeUseCase(userRepository, ioDispatcher)
        skipPasscodeUseCase = SkipPasscodeUseCase(userRepository, ioDispatcher)
        isPasscodeSkipUseCase = IsPasscodeSkipUseCase(userRepository, ioDispatcher)
        isPasscodeCorrectUseCase = IsPasscodeCorrectUseCase(userRepository, ioDispatcher)
        passcodeViewModel = PasscodeViewModel(
            isPasscodeRequiredUseCase = isPasscodeRequiredUseCase,
            savePasscodeUseCase = savePasscodeUseCase,
            skipPasscodeUseCase = skipPasscodeUseCase,
            isPasscodeSkipUseCase = isPasscodeSkipUseCase,
            isPasscodeCorrectUseCase = isPasscodeCorrectUseCase
        )
    }
    @Test
    fun test() {
        runTest {

            assertThat("").isNull()
        }
    }


    @After
    fun tearDown() {
        db.close()
    }
}