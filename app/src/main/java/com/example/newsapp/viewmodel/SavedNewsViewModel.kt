package com.example.newsapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.DeleteNewsUseCase
import com.example.newsapp.domain.IsNewsSavedUseCase
import com.example.newsapp.domain.SaveNewsUseCase
import com.example.newsapp.ui.state.SavedNewsUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SavedNewsViewModel @Inject constructor(
    private val isNewsSavedUseCase: IsNewsSavedUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val deleteNewsUseCase: DeleteNewsUseCase,
) : ViewModel() {
    var uiState by mutableStateOf(SavedNewsUIState())
        private set

    fun isSavedNews() {
        viewModelScope.launch {
            uiState = uiState.copy(isSaved = isNewsSavedUseCase.invoke(uiState.url))
        }
    }

    fun deleteNews() {
        viewModelScope.launch {
            deleteNewsUseCase.invoke(uiState.url)
            uiState = uiState.copy(isSaved = isNewsSavedUseCase.invoke(uiState.url))
        }
    }

    fun saveNews() {
        viewModelScope.launch {
            saveNewsUseCase.invoke(uiState.url)
            uiState = uiState.copy(isSaved = isNewsSavedUseCase.invoke(uiState.url))
        }
    }

    fun changeUrl(url: String) {
        runBlocking {
            uiState = uiState.copy(url = url)
            isSavedNews()
        }
    }
}