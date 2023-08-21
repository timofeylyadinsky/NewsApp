package com.example.newsapp

import com.example.newsapp.data.dao.UserDao
import com.example.newsapp.data.entity.User
import com.example.newsapp.data.repository.UserRepository
import com.example.newsapp.domain.IsPasscodeCorrectUseCase
import com.example.newsapp.domain.IsPasscodeRequiredUseCase
import com.example.newsapp.domain.IsPasscodeSkipUseCase
import com.example.newsapp.domain.SavePasscodeUseCase
import com.example.newsapp.domain.SkipPasscodeUseCase
import com.example.newsapp.viewmodel.PasscodeViewModel
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest

class PasscodeViewModelUnitTest {
    @MockK
    private lateinit var userDao: UserDao

    private lateinit var userRepo: UserRepository

    private lateinit var isPasscodeRequiredUseCase: IsPasscodeRequiredUseCase

    private lateinit var isPasscodeSkipUseCase: IsPasscodeSkipUseCase

    private lateinit var isPasscodeCorrectUseCase: IsPasscodeCorrectUseCase

    private lateinit var savePasscodeUseCase: SavePasscodeUseCase

    private lateinit var skipPasscodeUseCase: SkipPasscodeUseCase

    private lateinit var passcodeViewModel: PasscodeViewModel

    private val ioDispatcher = Dispatchers.IO

    @Before
    fun setup() {
        userDao = mockk(relaxed = true)
        userRepo = UserRepository(userDao)
        isPasscodeRequiredUseCase = IsPasscodeRequiredUseCase(userRepo, ioDispatcher)
        isPasscodeSkipUseCase = IsPasscodeSkipUseCase(userRepo, ioDispatcher)
        isPasscodeCorrectUseCase = IsPasscodeCorrectUseCase(userRepo, ioDispatcher)
        savePasscodeUseCase = SavePasscodeUseCase(userRepo, ioDispatcher)
        skipPasscodeUseCase = SkipPasscodeUseCase(userRepo, ioDispatcher)
        passcodeViewModel = PasscodeViewModel(
            isPasscodeRequiredUseCase = isPasscodeRequiredUseCase,
            savePasscodeUseCase = savePasscodeUseCase,
            skipPasscodeUseCase = skipPasscodeUseCase,
            isPasscodeSkipUseCase = isPasscodeSkipUseCase,
            isPasscodeCorrectUseCase = isPasscodeCorrectUseCase
        )
        coEvery {
            userDao.getUser()
        } returns User(1, true, "1234")
        coEvery {
            userDao.saveUser(User(1, true, "1111"))
        } answers {}
    }

    @Test
    fun `test 1 changing passcode in ui state`() {
        passcodeViewModel.changePasscodeValue("1111")
        assertThat(passcodeViewModel.uiState.passcode).isEqualTo("1111")
    }

    @Test
    fun `test 2 changing passcode in ui state`() {
        passcodeViewModel.changePasscodeValue("1234")
        assertThat(passcodeViewModel.uiState.passcode).isEqualTo("1234")
    }

    @Test
    fun `test passcode change when add more than 4 num`() {
        passcodeViewModel.uiState.passcode = "1234"
        passcodeViewModel.changePasscodeValue("12345")
        assertThat(passcodeViewModel.uiState.passcode).isEqualTo("1234")
    }

    @Test
    fun `test submit without 4 num in passcode`() {
        passcodeViewModel.clickSubmitButton()
        assertThat(passcodeViewModel.uiState.errorMessage).isEqualTo(2131492923)
    }

    @Test
    fun `test submit not correct passcode`() {
        passcodeViewModel.uiState.passcode = "1111"
        passcodeViewModel.clickSubmitButton()
        assertThat(passcodeViewModel.uiState.errorMessage).isEqualTo(2131492920)
    }

    @Test
    fun `test submit correct passcode`() {
        passcodeViewModel.uiState.passcode = "1234"
        passcodeViewModel.clickSubmitButton()
        assertThat(passcodeViewModel.uiState.errorMessage).isEqualTo(2131492880)
    }

    @After
    fun tearDown() {
    }
}