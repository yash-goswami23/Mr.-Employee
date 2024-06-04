package com.example.emp.Domain.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emp.data.FirebaseAuth.AuthRepositoryImpl
import com.example.emp.data.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl
) : ViewModel() {

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow : StateFlow<Resource<FirebaseUser>?> = _loginFlow

    private val _signupFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signupFlow : StateFlow<Resource<FirebaseUser>?> = _signupFlow

    val currentUser: FirebaseUser?
        get() = authRepository.currentUser
    init {
        if(authRepository.currentUser != null){
            _loginFlow.value = Resource.Success(authRepository.currentUser!!)
        }
    }


    fun login(email: String, password: String) = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = authRepository.login(email, password)
        _loginFlow.value = result
    }

    fun signup(name: String,email: String, password: String) = viewModelScope.launch {
        _signupFlow.value = Resource.Loading
        val result = authRepository.signup(name, email, password)
        _signupFlow.value = result
    }

    fun logout() = viewModelScope.launch {
        authRepository.logout()
        _loginFlow.value = null
        _signupFlow.value = null
    }


}

