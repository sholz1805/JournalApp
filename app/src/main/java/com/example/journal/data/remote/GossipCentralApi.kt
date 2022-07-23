package com.example.journal.data.remote

import com.example.journal.data.model.LoginRequest
import com.example.journal.data.model.LoginResponse
import com.example.journal.data.model.RegistrationRequest
import com.example.journal.data.model.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface GossipCentralApi {
    @POST("auth/user")
    suspend fun registerUser(@Body request : RegistrationRequest) : RegistrationResponse

    @POST("auth/authenticate")
    suspend fun loginUser(@Body request : LoginRequest) : LoginResponse
}