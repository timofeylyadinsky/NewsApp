package com.example.newsapp.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsapp.R
import com.example.newsapp.ui.navigation.Screen
import com.example.newsapp.viewmodel.PasscodeViewModel

@Composable
fun PasscodeScreen(
    passcodeViewModel: PasscodeViewModel = hiltViewModel(),
    navController: NavController
) {
    if (!passcodeViewModel.uiState.isPasscodeSkip) {
        PasscodeFieldRow(navController = navController)
        PasscodeStartScreen(navController = navController)
    } else {
        navController.navigate(Screen.NewsListScreen.route) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }
}

@Composable
fun PasscodeFieldRow(
    passcodeViewModel: PasscodeViewModel = hiltViewModel(),
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = stringResource(id = passcodeViewModel.uiState.errorMessage),
            style = TextStyle(
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            TextField(
                value = passcodeViewModel.uiState.passcode,
                onValueChange = { passcodeViewModel.changePasscodeValue(it) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                modifier = Modifier
                    .padding(horizontal = 0.dp)
                    .fillMaxWidth(0.5f),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                    textColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
            Button(
                onClick = {
                    passcodeViewModel.clickSubmitButton()
                    if (passcodeViewModel.uiState.isNavigateNextScreen) {
                        navController.navigate(Screen.NewsListScreen.route) {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                    /*TODO() Next Screen*/
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.submit),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
fun PasscodeStartScreen(
    viewModel: PasscodeViewModel = hiltViewModel(),
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(viewModel.uiState.welcomeMessageID),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        AnimatedVisibility(visible = viewModel.uiState.isShowSkipButton) {
            Text(
                text = stringResource(id = R.string.create_skip),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(visible = viewModel.uiState.isShowSkipButton) {
            Button(
                onClick = {
                    viewModel.skipPasscode()
                    navController.navigate(Screen.NewsListScreen.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                    /*TODO() Next Screen*/
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.skip),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}