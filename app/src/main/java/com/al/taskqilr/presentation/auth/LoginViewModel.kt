package com.al.taskqilr.presentation.auth

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.al.taskqilr.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _loginToken = MutableStateFlow<String?>(null)
    val loginToken: StateFlow<String?> = _loginToken


    fun saveToken(token: String) {
        _loginToken.value = token
    }
}