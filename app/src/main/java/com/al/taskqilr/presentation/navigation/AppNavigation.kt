package com.al.taskqilr.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.al.taskqilr.presentation.auth.LoginScreen
import com.al.taskqilr.presentation.auth.LoginViewModel
import com.al.taskqilr.presentation.home.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            // Use hiltViewModel() to inject LoginViewModel
            val viewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = { token ->
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true } // Remove login from backstack
                    }
                }
            )
        }
        composable("home") {
            HomeScreen()
        }
    }
}
