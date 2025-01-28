package com.al.taskqilr.services

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.al.taskqilr.data.repository.DataRepository
import com.al.taskqilr.presentation.auth.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthentication @Inject constructor(
    private val auth: FirebaseAuth,
    private val dataRepository: DataRepository,
    private val authViewModel: AuthViewModel
) {

    fun getSignInIntent(context: Context): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("528000822377-uutrvsurskgdaj69d15od14n7smppska.apps.googleusercontent.com")
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(context, gso).signInIntent
    }

    suspend fun handleSignInResult(data: Intent?): FirebaseUser? {
        return try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)

            val accountJson = """
            {
                "id": "${account.id}",
                "email": "${account.email}",
                "displayName": "${account.displayName}",
                "photoUrl": "${account.photoUrl}",
                "idToken": "${account.idToken}",
                "requestedScope": "${account.requestedScopes}",
            }
        """.trimIndent()
            Log.d("Account", "$account")
            Log.d("FirebaseAuthentication", "Google Account JSON: $accountJson")
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            authViewModel.authenticate(account.idToken!!)
            auth.signInWithCredential(credential).await().user
        } catch (e: Exception) {
            Log.e("FirebaseAuthentication", "Google Sign-In failed", e)
            null
        }
    }
}