package com.example.newsapp.ui.navigation

sealed class Screen(val route: String) {
    object PasscodeScreen : Screen("passcode_screen")
    object NewsListScreen : Screen("news_list_screen")
    object NewsDetailsScreen : Screen("news_details_screen")
    object SavedNewsScreen : Screen("saved_news_screen")
}
