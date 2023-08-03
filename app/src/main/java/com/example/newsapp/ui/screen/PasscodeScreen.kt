package com.example.newsapp.ui.screen

import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp.domain.IsPasscodeRequiredUseCase
import com.example.newsapp.viewmodel.PasscodeViewModel


const val MAX_LENGTH = 4;

@Composable
@Preview(showBackground = true)
fun PasscodeRow() {
    var password by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        TextField(
            value = password,
            onValueChange = { if (it.length <= MAX_LENGTH) password = it },
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
                if (password.length < 4) {
                    Toast.makeText(context, "Passcode should have 4 numbers", Toast.LENGTH_LONG)
                        .show()
                } else {
                    /*TODO() check first time password*/
                    /*TODO() Next Screen*/
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                text = "Submit",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FirstTimePasscodeStartScreen(
    viewModel: PasscodeViewModel = hiltViewModel()
) {
    PasscodeRow()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to News App",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        Text(
            text = "Create or Skip passcode",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
    }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                /*TODO() Save User without password*/
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            )
        ) {
            Text(
                text = "Skip",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SecondTimePasscodeStartScreen() {
    PasscodeRow()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome!",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
    }
}