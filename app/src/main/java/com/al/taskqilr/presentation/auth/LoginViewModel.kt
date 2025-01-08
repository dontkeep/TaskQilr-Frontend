package com.al.taskqilr.presentation.auth

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.al.taskqilr.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _loginResult = mutableStateOf<String?>(null) // Mutable state
    val loginResult: State<String?> get() = _loginResult

    fun login() {
        viewModelScope.launch {
            val token = loginUseCase()
            _loginResult.value = token
        }
    }
}