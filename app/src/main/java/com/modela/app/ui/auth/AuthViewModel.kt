package com.modela.app.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.modela.app.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _loginState = MutableLiveData<Resource<Boolean>>()
    val loginState: LiveData<Resource<Boolean>> = _loginState

    private val _registerState = MutableLiveData<Resource<Boolean>>()
    val registerState: LiveData<Resource<Boolean>> = _registerState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = Resource.Loading()
            delay(1500) // Simulate network call
            if (email.isNotEmpty() && password.length >= 6) {
                _loginState.value = Resource.Success(true)
            } else {
                _loginState.value = Resource.Error("Invalid email or password")
            }
        }
    }

    fun register(name: String, email: String, password: String, confirmPassword: String, userType: String) {
        viewModelScope.launch {
            _registerState.value = Resource.Loading()
            delay(1500) // Simulate network call
            when {
                name.isEmpty() -> _registerState.value = Resource.Error("Name is required")
                email.isEmpty() -> _registerState.value = Resource.Error("Email is required")
                password.length < 6 -> _registerState.value = Resource.Error("Password must be at least 6 characters")
                password != confirmPassword -> _registerState.value = Resource.Error("Passwords don't match")
                else -> _registerState.value = Resource.Success(true)
            }
        }
    }
}
