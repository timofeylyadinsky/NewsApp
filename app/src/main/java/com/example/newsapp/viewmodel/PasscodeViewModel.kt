package com.example.newsapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.IsPasscodeCorrectUseCase
import com.example.newsapp.domain.IsPasscodeRequiredUseCase
import com.example.newsapp.domain.IsPasscodeSkipUseCase
import com.example.newsapp.domain.SavePasscodeUseCase
import com.example.newsapp.domain.SkipPasscodeUseCase
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
    private val isPasscodeCorrectUseCase: IsPasscodeCorrectUseCase
) : ViewModel() {

    fun isFirstStart(): Boolean = runBlocking {
        isPasscodeRequiredUseCase.invoke()
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

    fun isPasscodeSkip(): Boolean = runBlocking {
        isPasscodeSkipUseCase.invoke()
    }

    fun isPasscodeCorrect(passcode: String): Boolean = runBlocking {
        isPasscodeCorrectUseCase.invoke(passcode)
    }
}