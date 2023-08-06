package com.example.newsapp.ui.state

import com.example.newsapp.data.entity.User

data class PasscodeUIState(
    val user: User? = null,
    val isLocked: Boolean = true,
    val hasPasscode: Boolean = false
)
