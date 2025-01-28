package com.al.taskqilr.presentation.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.al.taskqilr.data.network.responses.AuthResponse
import com.al.taskqilr.data.repository.DataRepository
import com.al.taskqilr.domain.manager.TokenStorage
import com.al.taskqilr.domain.state.AuthState
import com.al.taskqilr.presentation.home.HomeViewModel
import com.al.taskqilr.services.FirebaseAuthentication
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val dataRepository: DataRepository
) : ViewModel() {
    private var homeViewModel = HomeViewModel(dataRepository)

    private val _currentUser = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _jwtToken = MutableStateFlow<String?>(null)
    val jwtToken: StateFlow<String?> = _jwtToken.asStateFlow()

    init {
        _currentUser.value = auth.currentUser
        _isLoading.value = false

        auth.addAuthStateListener { firebaseAuth ->
            _currentUser.value = firebaseAuth.currentUser
        }
    }

    fun signIn(authClient: FirebaseAuthentication, context: ComponentActivity, launcher: ActivityResultLauncher<Intent>) {
        launcher.launch(authClient.getSignInIntent(context))
    }

    fun signOut(context: Context, jwtToken: String) {
        auth.signOut()
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.DEFAULT_SIGN_IN
        ).signOut()
        _currentUser.value = null
        Log.d("AuthViewModel", "jwtToken: ${jwtToken}")
        jwtToken.let { homeViewModel.logout(it) }
        _jwtToken.value = null
    }

    fun authenticate(idToken: String) {
        viewModelScope.launch {
            dataRepository.authenticate(idToken).enqueue(
                object : retrofit2.Callback<AuthResponse> {
                    override fun onResponse(
                        call: Call<AuthResponse>,
                        response: retrofit2.Response<AuthResponse>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let { authResponse ->
                                _jwtToken.value = authResponse.token
                                Log.d("AuthViewModel", "Received token: ${authResponse.token}")
                            } ?: run {
                                Log.e("AuthViewModel", "Null response body")
                            }
                        }
                    }

                    override fun onFailure(
                        p0: Call<AuthResponse?>,
                        p1: Throwable
                    ) {
                        Log.e("AuthViewModel", "Authentication failed", p1)
                    }
                }
            )
        }
    }
}