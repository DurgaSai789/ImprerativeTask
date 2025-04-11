package com.example.imprerativetask.composable.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = LoginModelRepository()

    private val _loginState = MutableLiveData<ApiStateHandle>(ApiStateHandle.Idle)
    val loginState: LiveData<ApiStateHandle> = _loginState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = ApiStateHandle.Loading

            val result = repository.login(username, password)

            if (result.isSuccess) {
                val token = result.getOrNull() ?: ""
                _loginState.value = ApiStateHandle.Success(token)
            } else {
                val errorMessage = result.exceptionOrNull()?.message ?: "Unknown error"
                _loginState.value = ApiStateHandle.Error(errorMessage)
            }
        }
    }
}


sealed class ApiStateHandle {
    data object Idle : ApiStateHandle()
    data object Loading : ApiStateHandle()
    data class Success(val token: String) : ApiStateHandle()
    data class Error(val message: String) : ApiStateHandle()
}
