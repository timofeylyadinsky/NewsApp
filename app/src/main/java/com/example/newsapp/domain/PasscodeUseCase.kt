package com.example.newsapp.domain

import android.util.Log
import com.example.newsapp.data.entity.User
import com.example.newsapp.data.repository.UserRepository
import javax.inject.Inject

class PasscodeUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun checkStartApplication() = userRepository.getUserInfo().isEmpty()


    suspend fun firstStartPasscode(passcode: String) {
        userRepository.saveUser(User(isLocked = true, passcode = passcode.toInt()))
    }

    suspend fun firstStartPasscodeSkip() {
        userRepository.saveUser(User(isLocked = false, passcode = 0))
    }
}