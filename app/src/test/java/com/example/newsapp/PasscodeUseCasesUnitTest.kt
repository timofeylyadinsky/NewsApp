package com.example.newsapp

import com.example.newsapp.data.dao.UserDao
import com.example.newsapp.data.entity.User
import com.example.newsapp.data.repository.UserRepository
import com.example.newsapp.domain.IsPasscodeCorrectUseCase
import com.example.newsapp.domain.IsPasscodeRequiredUseCase
import com.example.newsapp.domain.IsPasscodeSkipUseCase
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

    private val ioDispatcher = Dispatchers.IO

    @Before
    fun setUp() {
        userDao = mockk()
        userRepo = UserRepository(userDao)
        isPasscodeRequiredUseCase = IsPasscodeRequiredUseCase(userRepo, ioDispatcher)
        isPasscodeSkipUseCase = IsPasscodeSkipUseCase(userRepo, ioDispatcher)
        isPasscodeCorrectUseCase = IsPasscodeCorrectUseCase(userRepo, ioDispatcher)
        coEvery {
            userDao.getUser()
        } returns User(1, true, "1111")
    }

    @Test
    fun `test passcode not required`() {
        runTest {
            assertThat(isPasscodeRequiredUseCase()).isFalse()
        }
    }

    @Test
    fun `test when passcode not skip use case`() {
        runTest {
            assertThat(isPasscodeSkipUseCase()).isFalse()
        }
    }

    @Test
    fun `test when passcode not correct`() {
        runTest {
            assertThat(isPasscodeCorrectUseCase("")).isFalse()
        }
    }

    @Test
    fun `test when passcode correct`() {
        runTest {
            assertThat(isPasscodeCorrectUseCase("1111")).isTrue()
        }
    }

    @Test
    fun `test passcode required`() {
        runTest {
            coEvery {
                userDao.getUser()
            } returns null
            assertThat(isPasscodeRequiredUseCase()).isTrue()
        }
    }

    @Test
    fun `test passcode skip`() {
        runTest {
            coEvery {
                userDao.getUser()
            } returns User(1, false, "")
            assertThat(isPasscodeSkipUseCase()).isTrue()
        }
    }

    @After
    fun tearDown(){}
}
