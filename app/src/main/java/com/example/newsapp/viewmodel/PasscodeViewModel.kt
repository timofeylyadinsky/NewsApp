package com.example.newsapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun firstStart() {
        viewModelScope.launch {
            uiState = uiState.copy(isFirst = isPasscodeRequiredUseCase(), isLocked = isPasscodeSkipUseCase())
        }
    }

    fun savePasscode(passcode: String) {
        viewModelScope.launch {
            savePasscodeUseCase.invoke(passcode)
            uiState = uiState.copy(isLocked = true, isFirst = false)
        }
    }

    fun skipPasscode() {
        viewModelScope.launch {
            skipPasscodeUseCase.invoke()
            uiState = uiState.copy(isLocked = false, isFirst = false)
        }
    }

    fun isPasscodeSkip(): Boolean = runBlocking {
        isPasscodeSkipUseCase()
    }

    fun isPasscodeCorrect(passcode: String): Boolean = runBlocking {
        isPasscodeCorrectUseCase.invoke(passcode)
    }
}