package com.example.newsapp.domain

import com.example.newsapp.data.entity.User
import com.example.newsapp.data.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class PasscodeUseCases @Inject constructor(private val userRepository: UserRepository) {

    suspend fun isFirstStartApplication() = userRepository.getUserInfo() == null


    suspend fun savePasscodeFirstTime(passcode: String) {
        userRepository.saveUser(User(isLocked = true, passcode = passcode.toInt()))
    }

    suspend fun skipPasscodeFirstTime() {
        userRepository.saveUser(User(isLocked = false, passcode = 0))
    }
}

class IsPasscodeRequiredUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
    ) {

}

class SavePasscodeUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

}

class SkipPasscodeUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

}