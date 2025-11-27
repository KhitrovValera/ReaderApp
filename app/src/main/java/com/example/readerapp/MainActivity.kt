package com.example.readerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.lifecycle.lifecycleScope
import com.example.readerapp.navigation.AppNavigation
import com.example.readerapp.screens.login.AuthViewModel
import com.example.readerapp.ui.theme.ReaderAppTheme
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var credentialManager: CredentialManager

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        credentialManager = CredentialManager.create(this)

        enableEdgeToEdge()
        setContent {
            ReaderAppTheme {
                AppNavigation(
                    authViewModel,
                    ::signInWithGoogle
                )
            }
        }
    }

    fun signInWithGoogle() {
        val googleIdOption = GetSignInWithGoogleOption.Builder(
            getString(R.string.default_web_client_id)
        ).build()

        val request = GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build()

        lifecycleScope.launch {
            try {
                val result = credentialManager.getCredential(
                    context = baseContext, request = request
                )

                handleCredentialResult(result.credential)

            } catch (_: GetCredentialCancellationException) {

            } catch (e: Exception) {
                authViewModel.setError(e.localizedMessage ?: "Ошибка авторизации при помощи Google")
            }
        }
    }

    fun handleCredentialResult(credential: Credential) {
        if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

            authViewModel.authWithGoogle(googleIdTokenCredential.idToken)
        } else {
            authViewModel.setError("Ошибка авторизации при помощи Google")
        }
    }

}