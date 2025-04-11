package com.example.imprerativetask.composable.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imprerativetask.retrofit.RetrofitClient
import com.example.imprerativetask.retrofit.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListScreenViewModel : ViewModel() {

    private val apiService = RetrofitClient.apiService

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions

    private val _loading = MutableStateFlow(true)
    val loading : StateFlow<Boolean> = _loading


    fun getTransactions(token: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getTransactions(token)
                if (response.isSuccessful) {
                    _loading.value = false
                    _transactions.value = response.body() ?: emptyList()
                    Log.e("CHECKING_THE_BODY","${response.body() ?: emptyList()}")
                } else {
                    Log.e("CHECKING_THE_BODY", "Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("CHECKING_THE_BODY", "Exception: ${e.localizedMessage}")
            }
        }
    }
}
