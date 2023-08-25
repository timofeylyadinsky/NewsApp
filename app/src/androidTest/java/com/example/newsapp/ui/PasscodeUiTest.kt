package com.example.newsapp.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.MainActivity
import com.example.newsapp.ui.screen.PasscodeScreen
import com.example.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class PasscodeUiTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()


    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun test() {
//        composeRule.setContent {
//            NewsAppTheme {
//                PasscodeScreen(navController = rememberNavController())
//            }
//        }

        composeRule.onNodeWithText("Submit").performClick()
        composeRule.onNodeWithText("Passcode").assertIsDisplayed()
    }

    @After
    fun tearDown() {
    }
}