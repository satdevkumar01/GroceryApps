package com.sokhal.groceryapp.domain.repository

import com.sokhal.groceryapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun register(name: String, email: String, password: String): Flow<Result<Pair<String, User>>>
    
    suspend fun login(email: String, password: String): Flow<Result<Pair<String, User>>>
    
    suspend fun forgotPassword(email: String): Flow<Result<String>>
    
    suspend fun resetPassword(token: String, password: String): Flow<Result<String>>
    
    suspend fun updateUser(name: String? = null, email: String? = null, profilePicture: String? = null): Flow<Result<User>>
    
    suspend fun getCurrentUser(): User?
    
    suspend fun saveAuthToken(token: String)
    
    suspend fun getAuthToken(): String?
    
    suspend fun clearAuthToken()
}