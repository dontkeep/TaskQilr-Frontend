package com.al.taskqilr.presentation.auth

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.al.taskqilr.presentation.auth.LoginViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: (String) -> Unit // Callback for successful login
) {
    // Observe the login result using the state provided by the ViewModel

    val context = LocalContext.current
    val loginToken = viewModel.loginToken.collectAsStateWithLifecycle().value
    Log.d("Token", "Received token: $loginToken")
    LaunchedEffect(loginToken) {
        loginToken?.let { token ->
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
        Button(onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://precious-sure-lioness.ngrok-free.app/login"))
            startActivity(context, intent, null)
        }) {
            Text(text = "Login with Google")
        }
    }
}
