package com.example.newsapp.domain

import com.example.newsapp.data.entity.User
import com.example.newsapp.data.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PasscodeUseCases @Inject constructor(private val userRepository: UserRepository) {

    suspend fun savePasscodeFirstTime(passcode: String) {

    }

    suspend fun skipPasscodeFirstTime() {
        userRepository.saveUser(User(isLocked = false, passcode = 0))
    }
}

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
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(passcode: String) = withContext(defaultDispatcher) {
        userRepository.saveUser(User(isLocked = true, passcode = passcode.toInt()))
    }
}

class SkipPasscodeUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

}