package com.al.taskqilr.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.al.taskqilr.data.network.responses.LogoutResponse
import com.al.taskqilr.data.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val dataRepository: DataRepository): ViewModel() {
    private val _logoutMessage = MutableStateFlow<String?>(null)
    val logoutMessage: StateFlow<String?> = _logoutMessage.asStateFlow()

    fun logout(jwtToken: String) {
        viewModelScope.launch {
            dataRepository.logout(jwtToken).enqueue(
                object : retrofit2.Callback<LogoutResponse> {
                    override fun onResponse(
                        call: Call<LogoutResponse>,
                        response: retrofit2.Response<LogoutResponse>
                    ) {
                        if (response.isSuccessful) {
                            val logoutResponse = response.body()
                            if (logoutResponse != null) {
                                _logoutMessage.value = logoutResponse.message
                            }
                        }
                    }

                    override fun onFailure(
                        p0: Call<LogoutResponse?>,
                        p1: Throwable
                    ) {
                        _logoutMessage.value = "Logout failed"
                    }
                }
            )
        }
    }
}