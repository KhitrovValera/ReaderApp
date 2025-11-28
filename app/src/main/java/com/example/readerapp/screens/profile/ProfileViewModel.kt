package com.example.readerapp.screens.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readerapp.R
import com.example.readerapp.domain.model.User
import com.example.readerapp.domain.repository.ReaderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ReaderRepository
) : ViewModel() {

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val profileState = _profileState.asStateFlow()

    init {
        getUser()
    }

    fun getUser() {
        _profileState.value = ProfileState.Loading
        viewModelScope.launch {
            val result = repository.getAuthUser()
            _profileState.value = if (result != null) {
                ProfileState.Idle(result)
            } else {
                ProfileState.ProfileError()
            }
        }
    }


    fun changeUserData(name: String? = null, photoUri: String? = null) {
        viewModelScope.launch {
            val result = repository.updateUser(name, photoUri)
            result.fold(
                onSuccess = { getUser() },
                onFailure = { _profileState.value = ProfileState.ProfileError() }
            )
        }
    }

    fun signOut() {
        viewModelScope.launch {
            repository.signOut()
        }
    }




    sealed interface ProfileState {
        data class Idle(var user: User) : ProfileState
        object Loading : ProfileState
        data class ProfileError(val message: String = "Как?? чтобы видеть этот экран надо залогиниться") : ProfileState
    }


}