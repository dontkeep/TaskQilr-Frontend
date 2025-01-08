package com.al.taskqilr.presentation.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: (String) -> Unit // Callback for successful login
) {
    // Observe the login result using the state provided by the ViewModel
    val loginResult = viewModel.loginResult.value

    // React to loginResult changes
    LaunchedEffect(loginResult) {
        loginResult?.let { token ->
            onLoginSuccess(token) // Notify the parent about successful login
        }
    }

    // UI content
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { viewModel.login() }) {
            Text(text = "Login with Google")
        }
    }
}
