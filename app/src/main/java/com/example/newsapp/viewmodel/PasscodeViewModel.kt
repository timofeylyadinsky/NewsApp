package com.example.newsapp.viewmodel

import androidx.compose.runtime.structuralEqualityPolicy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.repository.UserRepository
import com.example.newsapp.domain.IsPasscodeCorrectUseCase
import com.example.newsapp.domain.IsPasscodeRequiredUseCase
import com.example.newsapp.domain.IsPasscodeSkipUseCase
import com.example.newsapp.domain.SavePasscodeUseCase
import com.example.newsapp.domain.SkipPasscodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasscodeViewModel @Inject constructor(
    private val isPasscodeRequiredUseCase: IsPasscodeRequiredUseCase,
    private val savePasscodeUseCase: SavePasscodeUseCase,
    private val skipPasscodeUseCase: SkipPasscodeUseCase,
    private val isPasscodeSkipUseCase: IsPasscodeSkipUseCase,
    private val isPasscodeCorrectUseCase: IsPasscodeCorrectUseCase
) : ViewModel() {
    fun isFirstStart(): Boolean {
        var isFirst = true
        viewModelScope.launch {
            isFirst = isPasscodeRequiredUseCase.invoke()
        }
        return isFirst
    }

    fun savePasscode(passcode: String) {
        viewModelScope.launch {
            savePasscodeUseCase.invoke(passcode)
        }
    }

    fun skipPasscode() {
        viewModelScope.launch {
            skipPasscodeUseCase.invoke()
        }
    }

    fun isPasscodeSkip() : Boolean {
        var isSkip = true
        viewModelScope.launch {
            isSkip = isPasscodeSkipUseCase.invoke()
        }
        return isSkip
    }

    fun isPasscodeCorrect(passcode: String) : Boolean {
        var isCorrect = true
        viewModelScope.launch {
            isCorrect = isPasscodeCorrectUseCase(passcode)
        }
        return isCorrect
    }
}