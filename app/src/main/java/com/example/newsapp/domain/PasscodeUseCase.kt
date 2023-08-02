package com.example.newsapp.domain

import android.util.Log
import com.example.newsapp.data.repository.UserRepository
import javax.inject.Inject

class PasscodeUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun checkStartApplication() {
        if(userRepository.getUserInfo().isEmpty())
            Log.d("!!!!", "true")
        else
            Log.d("!!!!", "false")

    }


}