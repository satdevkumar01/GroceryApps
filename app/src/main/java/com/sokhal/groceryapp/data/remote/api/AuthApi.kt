package com.sokhal.groceryapp.data.remote.api

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthApi {
    
    @POST("/auth/register")
    suspend fun register(@Body registerRequest: Map<String, String>): Map<String, Any>
    
    @POST("/auth/login")
    suspend fun login(@Body loginRequest: Map<String, String>): Map<String, Any>
    
    @POST("/auth/forgot-password")
    suspend fun forgotPassword(@Body forgotPasswordRequest: Map<String, String>): Map<String, Any>
    
    @POST("/auth/reset-password")
    suspend fun resetPassword(@Body resetPasswordRequest: Map<String, String>): Map<String, Any>
    
    @PUT("/auth/user")
    suspend fun updateUser(@Body updateUserRequest: Map<String, Any>): Map<String, Any>
}