package com.example.newsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.ui.screen.NewsListScreen
import com.example.newsapp.ui.screen.PasscodeScreen

@Composable
fun NewsAppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.PasscodeScreen.route) {
        composable(route = Screen.PasscodeScreen.route) {
            PasscodeScreen(navController = navController)
        }
        composable(route = Screen.NewsListScreen.route) {
            NewsListScreen(/*navController = navController*/)
        }
        composable(route = Screen.NewsDetailsScreen.route) {
        }
        composable(route = Screen.SavedNewsScreen.route) {
        }
    }
}