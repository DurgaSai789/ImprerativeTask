package com.example.imprerativetask.retrofit

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(@SerializedName("token") val token: String)
data class Transaction(val id: String, val amount: Double, val date: String)

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("transactions")
    suspend fun getTransactions(@Header("Authorization") token: String): Response<List<Transaction>>
}