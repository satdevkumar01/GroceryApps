package com.sokhal.groceryapp.domain.use_case.auth

import com.sokhal.groceryapp.domain.model.User
import com.sokhal.groceryapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(name: String, email: String, password: String): Flow<Result<Pair<String, User>>> {
        if (name.isBlank()) {
            return flow {
                emit(Result.failure(Exception("Name cannot be empty")))
            }
        }
        
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
        
        return repository.register(name, email, password)
    }
    
    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailRegex.toRegex())
    }
}