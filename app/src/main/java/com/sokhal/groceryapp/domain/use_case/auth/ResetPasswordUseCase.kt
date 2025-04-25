package com.sokhal.groceryapp.domain.use_case.auth

import com.sokhal.groceryapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(token: String, password: String): Flow<Result<String>> {
        if (token.isBlank()) {
            return flow {
                emit(Result.failure(Exception("Reset token cannot be empty")))
            }
        }
        
        if (password.isBlank()) {
            return flow {
                emit(Result.failure(Exception("Password cannot be empty")))
            }
        }
        
        if (password.length < 6) {
            return flow {
                emit(Result.failure(Exception("Password must be at least 6 characters")))
            }
        }
        
        return repository.resetPassword(token, password)
    }
}