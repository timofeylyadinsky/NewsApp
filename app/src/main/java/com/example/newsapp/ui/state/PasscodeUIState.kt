package com.example.newsapp.ui.state

data class PasscodeUIState(
    val welcomeMessageID: Int = 0,
    var passcode: String = "",
    val isShowSkipButton: Boolean = true,
    val isPasscodeSkip: Boolean = false,
    val errorMessage: String = ""
)
