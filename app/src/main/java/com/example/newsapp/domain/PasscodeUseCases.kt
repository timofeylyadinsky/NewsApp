package com.example.newsapp.domain

import com.example.newsapp.data.entity.User
import com.example.newsapp.data.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IsPasscodeRequiredUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
    ) {
    suspend operator fun invoke(): Boolean = withContext(defaultDispatcher) {
        userRepository.getUserInfo() == null
    }
}

class SavePasscodeUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(passcode: String) = withContext(ioDispatcher) {
        userRepository.saveUser(User(isLocked = true, passcode = passcode.toInt()))
    }
}

class SkipPasscodeUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(passcode: String) = withContext(ioDispatcher) {
        userRepository.saveUser(User(isLocked = false, passcode = 0))
    }
}