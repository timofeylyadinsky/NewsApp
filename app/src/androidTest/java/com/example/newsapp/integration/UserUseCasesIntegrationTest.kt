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
class UserUseCasesIntegrationTest {
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
    }

    @Test
    fun givenEmptyDBWhenCheckItThenReturnPasscodeRequired() {
        runTest {
            isPasscodeRequiredUseCase = IsPasscodeRequiredUseCase(userRepository, ioDispatcher)
            val actualValue = isPasscodeRequiredUseCase()
            assertThat(actualValue).isTrue()
        }
    }

    @Test
    fun givenUserWhenCheckPasscodeRequiredThenReturnPasscodeNotRequired() {
        runTest {
            val givenUser = lockedUser
            userDao.saveUser(givenUser)
            isPasscodeRequiredUseCase = IsPasscodeRequiredUseCase(userRepository, ioDispatcher)

            val actualValue = isPasscodeRequiredUseCase()

            assertThat(actualValue).isFalse()
        }
    }

    @Test
    fun givenPasscodeWhenSaveItThenExpectedUserWithPasscode() {
        runTest {
            val expectedUser = lockedUser
            savePasscodeUseCase = SavePasscodeUseCase(userRepository, ioDispatcher)

            savePasscodeUseCase(testPasscode)

            val actualUser = userDao.getUser()

            assertThat(actualUser).isEqualTo(expectedUser)
        }
    }

    @Test
    fun givenNoPasscodeWhenSkipPasscodeThenUserNotLocked() {
        runTest {
            val expectedUser = notLockedUser
            skipPasscodeUseCase = SkipPasscodeUseCase(userRepository, ioDispatcher)

            skipPasscodeUseCase()

            val actualUser = userDao.getUser()

            assertThat(actualUser).isEqualTo(expectedUser)
        }
    }

    @Test
    fun givenUserWithPasscodeWhenCheckSkipPasscodeThenReturnNo() {
        runTest {
            val expectedUser = lockedUser
            userDao.saveUser(expectedUser)

            isPasscodeSkipUseCase = IsPasscodeSkipUseCase(userRepository, ioDispatcher)

            val isPasscodeSkip = isPasscodeSkipUseCase()

            assertThat(isPasscodeSkip).isFalse()
        }
    }

    @Test
    fun givenUserWithoutPasscodeWhenCheckSkipPasscodeThenReturnYes() {
        runTest {
            val expectedUser = notLockedUser
            userDao.saveUser(expectedUser)

            isPasscodeSkipUseCase = IsPasscodeSkipUseCase(userRepository, ioDispatcher)

            val isPasscodeSkip = isPasscodeSkipUseCase()

            assertThat(isPasscodeSkip).isTrue()
        }
    }

    @Test
    fun givenCorrectPasscodeWhenCheckItThenReturnCorrect() {
        runTest {
            val user = lockedUser
            userDao.saveUser(user)
            val passcode = testPasscode
            isPasscodeCorrectUseCase = IsPasscodeCorrectUseCase(userRepository, ioDispatcher)

            val isCorrect = isPasscodeCorrectUseCase(passcode)

            assertThat(isCorrect).isTrue()
        }
    }

    @Test
    fun givenIncorrectPasscodeWhenCheckItThenReturnIncorrect() {
        runTest {
            val user = lockedUser
            userDao.saveUser(user)
            val passcode = "testPasscode"
            isPasscodeCorrectUseCase = IsPasscodeCorrectUseCase(userRepository, ioDispatcher)

            val isCorrect = isPasscodeCorrectUseCase(passcode)

            assertThat(isCorrect).isFalse()
        }
    }

    @After
    fun tearDown() {
        db.close()
    }
}