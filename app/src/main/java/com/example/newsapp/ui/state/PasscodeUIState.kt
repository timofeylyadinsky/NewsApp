package com.example.newsapp.ui.state

import com.example.newsapp.data.entity.User

data class PasscodeUIState(
    val isLocked: Boolean = true,
    val isFirst: Boolean = true,
    val isPasscodeCorrect: Boolean = false
)
