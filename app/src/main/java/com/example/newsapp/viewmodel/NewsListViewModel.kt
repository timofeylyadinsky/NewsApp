package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.DeleteNewsInDBUseCase
import com.example.newsapp.domain.GetNewsFromDBUseCase
import com.example.newsapp.domain.GetNewsResponseUseCase
import com.example.newsapp.domain.InsertCachedArticlesUseCase
import com.example.newsapp.ui.state.NewsListUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getNewsResponseUseCase: GetNewsResponseUseCase,
    private val getNewsFromDBUseCase: GetNewsFromDBUseCase,
    private val insertCachedArticlesUseCase: InsertCachedArticlesUseCase,
    private val deleteNewsInDBUseCase: DeleteNewsInDBUseCase
) : ViewModel() {

    val uiState = MutableStateFlow<NewsListUIState>(NewsListUIState.LOADING)

    init {
        uiState.tryEmit(NewsListUIState.LOADING)
        getList()
    }

//    private fun getList() = viewModelScope.launch {
//        uiState.tryEmit(NewsListUIState.LOADING)
//        try {
//            val newsData = getNewsResponseUseCase()
//            newsData.collect { data ->
//                if (data.errorMessage.isNullOrEmpty()) uiState.value =
//                    NewsListUIState.SUCCESS(data.news)
//                else uiState.value =
//                    NewsListUIState.ERROR(data.errorMessage)
//            }
//        } catch (e: Exception) {
//            uiState.value = NewsListUIState.ERROR(e.localizedMessage)
//        }
//    }

    private fun getList() = viewModelScope.launch {
        getNewsFromDBUseCase().collect {
            uiState.tryEmit(NewsListUIState.LOADING)
            uiState.value = NewsListUIState.SUCCESS(it)
        }
    }

    private fun fetchList() = viewModelScope.launch {  }
}