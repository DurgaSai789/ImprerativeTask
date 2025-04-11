package com.example.imprerativetask.composable.login

import com.example.imprerativetask.retrofit.LoginRequest
import com.example.imprerativetask.retrofit.RetrofitClient
import com.example.imprerativetask.secureStorage.SecureStorage

class LoginModelRepository {

    suspend fun login(username: String, password: String): Result<String> {
        return try {
            val response = RetrofitClient.apiService.login(LoginRequest(username, password))
            if (response.isSuccessful) {
                val token = response.body()?.token ?: return Result.failure(Exception("Token not found"))
                SecureStorage.saveToken(token)
                Result.success(token)
            } else {
                Result.failure(Exception("Login failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
