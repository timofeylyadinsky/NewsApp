package com.example.newsapp.ui.screen

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp.viewmodel.SavedNewsViewModel

@Composable
fun NewsDetailsScreen(
    url: String,
    savedNewsViewModel: SavedNewsViewModel = hiltViewModel()
) {
    savedNewsViewModel.changeUrl(url)
    val state = savedNewsViewModel.uiState
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (state.isSaved) {
                    savedNewsViewModel.deleteNews()
                } else {
                    savedNewsViewModel.saveNews()
                }
            }) {
                Crossfade(targetState = state.isSaved, label = "") {
                    if (it) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Clear icon",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add icon",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
                }
            }
        }
    ) { padding ->
        AndroidView(
            factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    loadUrl(url)
                }
            },
            update = {
                it.loadUrl(url)
            },
            modifier = Modifier.padding(padding)
        )
    }
}