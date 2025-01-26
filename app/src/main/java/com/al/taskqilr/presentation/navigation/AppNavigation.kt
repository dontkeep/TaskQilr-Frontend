package com.al.taskqilr.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.al.taskqilr.domain.state.AuthState
import com.al.taskqilr.presentation.auth.AuthViewModel
import com.al.taskqilr.presentation.home.HomeScreen
import androidx.compose.runtime.getValue
import com.al.taskqilr.presentation.auth.LoginScreen

@Composable
fun AppNavigation(
    onGoogleSignIn: () -> Unit,
    onLogout: () -> Unit,
    isUserLoggedIn: Boolean
) {
    val navController = rememberNavController()

    LaunchedEffect(isUserLoggedIn) {
        if (isUserLoggedIn) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        } else {
            navController.navigate("login") {
                popUpTo("home") { inclusive = true }
            }
        }
    }

    NavHost(navController = navController, startDestination = if (isUserLoggedIn) "home" else "login") {
        composable("login") {
            LoginScreen(onSignInClick = onGoogleSignIn)
        }

        composable("home") {
            HomeScreen(onLogout = onLogout)
        }
    }
}