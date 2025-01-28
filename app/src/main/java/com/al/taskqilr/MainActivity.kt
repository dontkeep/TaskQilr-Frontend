package com.al.taskqilr

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.al.taskqilr.data.repository.DataRepository
import com.al.taskqilr.presentation.auth.AuthViewModel
import com.al.taskqilr.presentation.home.HomeViewModel
import com.al.taskqilr.presentation.navigation.AppNavigation
import com.al.taskqilr.presentation.theme.TaskQilrTheme
import com.al.taskqilr.services.FirebaseAuthentication
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.migration.CustomInjection.inject
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    @Inject
    lateinit var dataRepository: DataRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskQilrTheme {
                val homeViewModel = hiltViewModel<HomeViewModel>()
                val authViewModel = hiltViewModel<AuthViewModel>()
                val authClient = remember { FirebaseAuthentication(FirebaseAuth.getInstance(), dataRepository, authViewModel) }
                val currentUserState = authViewModel.currentUser.collectAsStateWithLifecycle()
                val isLoadingState = authViewModel.isLoading.collectAsStateWithLifecycle()
                val currentUser = currentUserState.value
                val isLoading = isLoadingState.value

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartActivityForResult()
                ) { result ->
                    lifecycleScope.launch {
                        val user = authClient.handleSignInResult(result.data)
                        if (user == null) {
                            !isLoading
                        } else {
                            Log.d("MainActivity", "User is not null")
                        }
                    }
                }

                AppNavigation(
                    onGoogleSignIn = {
                        authViewModel.signIn(authClient, this, launcher)
                    },
                    onLogout = {
                        val token = authViewModel.jwtToken.value
                        Log.d("MainActivity", "Token: $token")
                        authViewModel.signOut(this, token.toString())
                               },
                    isUserLoggedIn = !isLoading && currentUser != null
                )
            }
        }
    }
}
