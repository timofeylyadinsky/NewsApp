package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.newsapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PasscodeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

}