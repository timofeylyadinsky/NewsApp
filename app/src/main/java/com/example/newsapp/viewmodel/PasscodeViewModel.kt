package com.example.newsapp.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.example.newsapp.R
import com.example.newsapp.data.repository.UserRepository
import com.example.newsapp.domain.IsPasscodeCorrectUseCase
import com.example.newsapp.domain.IsPasscodeRequiredUseCase
import com.example.newsapp.domain.IsPasscodeSkipUseCase
import com.example.newsapp.domain.SavePasscodeUseCase
import com.example.newsapp.domain.SkipPasscodeUseCase
import com.example.newsapp.ui.state.PasscodeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

private const val MAX_LENGTH = 4;

@HiltViewModel
class PasscodeViewModel @Inject constructor(
    private val isPasscodeRequiredUseCase: IsPasscodeRequiredUseCase,
    private val savePasscodeUseCase: SavePasscodeUseCase,
    private val skipPasscodeUseCase: SkipPasscodeUseCase,
    private val isPasscodeSkipUseCase: IsPasscodeSkipUseCase,
    private val isPasscodeCorrectUseCase: IsPasscodeCorrectUseCase,
) : ViewModel() {

    var uiState by mutableStateOf(PasscodeUIState())
        private set

    init {
        runBlocking {
            uiState = uiState.copy(
                isShowSkipButton = isPasscodeRequiredUseCase(),
                isPasscodeSkip = isPasscodeSkipUseCase(),
                welcomeMessageID = (if (isPasscodeRequiredUseCase())
                    (R.string.welcome_first)
                else
                    R.string.welcome_next)
            )
        }
    }

    fun changePasscodeValue(passcode: String) {
        if (passcode.length <= MAX_LENGTH) {
            uiState = uiState.copy(passcode = passcode)
        }
    }

    fun clickSubmitButton() {
        viewModelScope.launch {
            if (uiState.passcode.length < MAX_LENGTH) {
                uiState = uiState.copy(errorMessage = "Password not correct only 4 num")
            } else {
                if (isPasscodeRequiredUseCase()) {
                    savePasscodeUseCase.invoke(passcode = uiState.passcode)
                    /*TODO() next screen*/
                } else if (isPasscodeCorrectUseCase.invoke(uiState.passcode)) {
                    uiState = uiState.copy(errorMessage = "Password correct")
                    /*TODO() next screen*/
                } else {
                    uiState = uiState.copy(errorMessage = "Password not correct")
                }
            }
        }
    }

    fun skipPasscode() {
        viewModelScope.launch {
            skipPasscodeUseCase()
        }
    }
}