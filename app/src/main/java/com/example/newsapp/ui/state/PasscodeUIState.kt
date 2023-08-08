package com.example.newsapp.ui.state

data class PasscodeUIState(
    val welcomeMessage: String = "",
    val passcode: String = "",
    val isShowSkipButton: Boolean = true,
    val errorMessage: String = "Password not correct"
)
