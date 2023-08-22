package com.example.newsapp

import com.example.newsapp.data.dao.UserDao
import com.example.newsapp.data.entity.User
import com.example.newsapp.data.repository.UserRepository
import com.example.newsapp.domain.IsPasscodeCorrectUseCase
import com.example.newsapp.domain.IsPasscodeRequiredUseCase
import com.example.newsapp.domain.IsPasscodeSkipUseCase
import com.example.newsapp.domain.SavePasscodeUseCase
import com.example.newsapp.domain.SkipPasscodeUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class PasscodeUseCasesUnitTest {

    @MockK
    private lateinit var userDao: UserDao

    private lateinit var userRepo: UserRepository

    private lateinit var isPasscodeRequiredUseCase: IsPasscodeRequiredUseCase

    private lateinit var isPasscodeSkipUseCase: IsPasscodeSkipUseCase

    private lateinit var isPasscodeCorrectUseCase: IsPasscodeCorrectUseCase

    private lateinit var savePasscodeUseCase: SavePasscodeUseCase

    private lateinit var skipPasscodeUseCase: SkipPasscodeUseCase

    private val ioDispatcher = Dispatchers.IO

    @Before
    fun setUp() {
        userDao = mockk()
        userRepo = UserRepository(userDao)
    }

    @Test
    fun `Given user When start app Then receive not empty user info`() {
        runTest {
            isPasscodeRequiredUseCase = IsPasscodeRequiredUseCase(userRepo, ioDispatcher)
            coEvery {
                userDao.getUser()
            } returns User(1, true, "1111")

            assertThat(isPasscodeRequiredUseCase()).isFalse()
        }
    }

    @Test
    fun `Given locked user When passcode not skip use case Then receive message is locked`() {
        runTest {
            isPasscodeSkipUseCase = IsPasscodeSkipUseCase(userRepo, ioDispatcher)
            coEvery {
                userDao.getUser()
            } returns User(1, true, "1111")

            assertThat(isPasscodeSkipUseCase()).isFalse()
        }
    }

    @Test
    fun `Given locked user When send not correct passcode Then return passcode incorrect`() {
        runTest {
            isPasscodeCorrectUseCase = IsPasscodeCorrectUseCase(userRepo, ioDispatcher)
            coEvery {
                userDao.getUser()
            } returns User(1, true, "1111")
            
            assertThat(isPasscodeCorrectUseCase("")).isFalse()
        }
    }

    @Test
    fun `Given locked user When send correct passcode Then return passcode correct`() {
        runTest {

            isPasscodeCorrectUseCase = IsPasscodeCorrectUseCase(userRepo, ioDispatcher)
            coEvery {
                userDao.getUser()
            } returns User(1, true, "1111")

            assertThat(isPasscodeCorrectUseCase("1111")).isTrue()
        }
    }

    @Test
    fun `Given null user When check for existing Then return passcode required`() {
        runTest {
            //Given empty user entity
            isPasscodeRequiredUseCase = IsPasscodeRequiredUseCase(userRepo, ioDispatcher)
            coEvery {
                userDao.getUser()
            } returns null
            //When Check Then received passcode required
            assertThat(isPasscodeRequiredUseCase()).isTrue()
        }
    }

    @Test
    fun `Given user not locked When check skipping passcode Return user not locked`() {
        runTest {
            //Given not locked user
            isPasscodeSkipUseCase = IsPasscodeSkipUseCase(userRepo, ioDispatcher)
            coEvery {
                userDao.getUser()
            } returns User(1, false, "")

            assertThat(isPasscodeSkipUseCase()).isTrue()
        }
    }

    @Test
    fun `Given user entity with passcode When save it Then receive similar user entity with passcode`() {
        runTest {
            //Given passcode to save
            savePasscodeUseCase = SavePasscodeUseCase(userRepo, ioDispatcher)
            val user = User(isLocked = true, passcode = "1234")
            var savedUser = ""
            coEvery {
                userDao.saveUser(user)
            } answers { savedUser = user.toString() }
            //When save passcode user
            savePasscodeUseCase("1234")
            //Then
            assertThat(savedUser).isEqualTo(user.toString())
        }
    }

    @Test
    fun `Given user entity without passcode When save it Then receive similar user entity without passcode`() {
        runTest {
            //Given user entity without passcode
            skipPasscodeUseCase = SkipPasscodeUseCase(userRepo, ioDispatcher)
            val user = User(isLocked = false, passcode = "")
            var savedUser = ""
            coEvery {
                userDao.saveUser(user)
            } answers { savedUser = user.toString() }
            //When save it
            skipPasscodeUseCase()
            //Then
            assertThat(savedUser).isEqualTo(user.toString())
        }
    }

    @After
    fun tearDown() {
    }
}
