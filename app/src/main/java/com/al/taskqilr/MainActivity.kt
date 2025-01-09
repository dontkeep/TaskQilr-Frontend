package com.al.taskqilr

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.al.taskqilr.presentation.auth.LoginViewModel
import com.al.taskqilr.presentation.navigation.AppNavigation
import com.al.taskqilr.presentation.theme.TaskQilrTheme
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var oneTapClient: SignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskQilrTheme {
                AppNavigation() // Launch the navigation graph
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("Running", "onNewIntent: called")
        intent.data?.let { uri ->
            Log.d("Uri Main", "Uri: $uri")
            if (uri.toString().startsWith("https://precious-sure-lioness.ngrok-free.app/callback")) {
                val token = uri.getQueryParameter("token")
                Log.d("Token Main", "Received token: $token")
                if (token != null) {
                    Log.d("Token", "Received token: $token")
                    loginViewModel.saveToken(token)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TaskQilrTheme {
        Greeting("Android")
    }
}