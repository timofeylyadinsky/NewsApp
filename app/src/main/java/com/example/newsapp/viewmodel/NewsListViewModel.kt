package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.GetNewsResponseUseCase
import com.example.newsapp.ui.state.NewsListUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getNewsResponseUseCase: GetNewsResponseUseCase
) : ViewModel() {

    val uiState = MutableStateFlow<NewsListUIState>(NewsListUIState.LOADING)

    init {
        getList()
    }

    private fun getList() = viewModelScope.launch {
        uiState.tryEmit(NewsListUIState.LOADING)
        try {
            val newsData = withContext(Dispatchers.IO) {
                getNewsResponseUseCase()
            }
            newsData.collect { data ->
                if (data.errorMessage.isNullOrEmpty()) uiState.value =
                    NewsListUIState.SUCCESS(data.news)
                else uiState.value =
                    NewsListUIState.ERROR(data.errorMessage)
            }
        } catch (e: Exception) {
            uiState.value = NewsListUIState.ERROR(e.localizedMessage)
        }
    }
}