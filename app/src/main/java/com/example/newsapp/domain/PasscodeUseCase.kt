package com.example.newsapp.domain

import android.util.Log
import com.example.newsapp.data.entity.User
import com.example.newsapp.data.repository.UserRepository
import javax.inject.Inject

class PasscodeUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun isFirstStartApplication() = userRepository.getUserInfo() == null


    suspend fun savePasscodeFirstTime(passcode: String) {
        userRepository.saveUser(User(isLocked = true, passcode = passcode.toInt()))
    }

    suspend fun skipPasscodeFirstTime() {
        userRepository.saveUser(User(isLocked = false, passcode = 0))
    }
}