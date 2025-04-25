package com.sokhal.groceryapp.domain.use_case.auth

import com.sokhal.groceryapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String): Flow<Result<String>> {
        if (email.isBlank()) {
            return flow {
                emit(Result.failure(Exception("Email cannot be empty")))
            }
        }
        
        if (!isValidEmail(email)) {
            return flow {
                emit(Result.failure(Exception("Invalid email format")))
            }
        }
        
        return repository.forgotPassword(email)
    }
    
    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailRegex.toRegex())
    }
}