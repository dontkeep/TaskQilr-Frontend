package com.al.taskqilr.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.al.taskqilr.presentation.auth.LoginViewModel

@Composable
fun HomeScreen() {
    // Get the ViewModel using hiltViewModel()
    val viewModel: LoginViewModel = hiltViewModel()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        DisplayToken(viewModel = viewModel)  // Pass the ViewModel to DisplayToken
    }
}

@Composable
fun DisplayToken(viewModel: LoginViewModel) {
    val token = viewModel.loginToken.collectAsStateWithLifecycle().value  // Collect token state

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        if (token != null) {
            Text("Token: $token")  // Display the token if it's available
        } else {
            Text("No token available")  // If the token is not available, display this message
        }
    }
}
