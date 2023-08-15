package com.example.newsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.ui.screen.NewsDetailsScreen
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
            NewsListScreen(navController = navController)
        }
        composable(
            route = Screen.NewsDetailsScreen.route + "/{url}",
            arguments = listOf(navArgument("url") {
                type = NavType.StringType
                defaultValue = "google.com"
                nullable = false
            })
        ) { entry ->
            entry.arguments?.getString("url")?.let { NewsDetailsScreen(url = it) }
        }
        composable(route = Screen.SavedNewsScreen.route) {
        }
    }
}