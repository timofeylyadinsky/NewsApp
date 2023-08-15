package com.example.newsapp.domain

import com.example.newsapp.data.entity.SavedNewsDbo
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.data.repository.module.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(url: String) = withContext(ioDispatcher) {
        newsRepository.saveNews(SavedNewsDbo(url = url))
    }
}

class DeleteNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(url: String) = withContext(ioDispatcher) {
        newsRepository.deleteNews(url)
    }
}
