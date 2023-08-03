package com.example.newsapp.domain

import com.example.newsapp.data.entity.User
import com.example.newsapp.data.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IsPasscodeRequiredUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(): Boolean = withContext(ioDispatcher) {
        userRepository.getUserInfo() == null
    }
}

class SavePasscodeUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(passcode: String) = withContext(ioDispatcher) {
        userRepository.saveUser(User(isLocked = true, passcode = passcode))
    }
}

class SkipPasscodeUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke() = withContext(ioDispatcher) {
        userRepository.saveUser(User(isLocked = false, passcode = ""))
    }
}

class IsPasscodeSkipUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(): Boolean = withContext(ioDispatcher) {
        userRepository.getUserInfo()?.isLocked == false
    }
}

class IsPasscodeCorrectUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(passcode: String): Boolean = withContext(ioDispatcher) {
        userRepository.getUserInfo()?.passcode == passcode
    }
}