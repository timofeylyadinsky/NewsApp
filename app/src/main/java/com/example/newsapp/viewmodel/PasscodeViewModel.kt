package com.example.newsapp.viewmodel

import androidx.compose.runtime.structuralEqualityPolicy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.repository.UserRepository
import com.example.newsapp.domain.IsPasscodeRequiredUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasscodeViewModel @Inject constructor(
    private val isPasscodeRequiredUseCase: IsPasscodeRequiredUseCase
) : ViewModel() {
    fun getNumOfStartApp(): Boolean {
        var isFirst = true
        viewModelScope.launch {
            isFirst = isPasscodeRequiredUseCase.invoke()
        }
        return isFirst
    }
}