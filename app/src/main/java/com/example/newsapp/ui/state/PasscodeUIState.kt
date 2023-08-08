package com.example.newsapp.ui.state

data class PasscodeUIState(
    val welcomeMessage: String = "",
    var passcode: String = "",
    val isShowSkipButton: Boolean = true,
    val isPasscodeSkip: Boolean = false,
    val errorMessage: String = "Password not correct"
)
