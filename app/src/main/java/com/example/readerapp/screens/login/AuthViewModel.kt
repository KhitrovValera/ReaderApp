package com.example.readerapp.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readerapp.domain.repository.ReaderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: ReaderRepository
): ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    private var _isRegister = MutableStateFlow(false)
    val isRegister = _isRegister.asStateFlow()

    private fun handlerAuthRequest(
        authFun: suspend () -> Result<Unit>
    ) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            val result = authFun()
            result.fold(
                onSuccess = {
                    _authState.value = AuthState.Success
                },
                onFailure = {
                    _authState.value = AuthState.AuthError(it.localizedMessage)
                }
            )
        }
    }

   fun authWithGoogle(idToken: String) {
       handlerAuthRequest {
           repository.authWithGoogle(idToken)
       }
   }

    fun authWithEmail(email: String?, password: String?, repeatPassword: String?) {
        if (email.isNullOrBlank() || password.isNullOrBlank()) return
        if (isRegister.value && password != repeatPassword) {
            setError("Пароли не совпадают")
            return
        }
        handlerAuthRequest {
            if (isRegister.value) repository.registerWithEmail(email, password) else repository.loginWithEmail(email, password)
        }
    }

    fun onSwitchModePressed() {
        _isRegister.value = !_isRegister.value
    }

    fun setError(message: String) {
        _authState.value = AuthState.AuthError(message)
    }


    sealed interface AuthState {
        object Idle : AuthState
        object Loading : AuthState
        data class AuthError(val message: String?) : AuthState
        object Success : AuthState
    }
}