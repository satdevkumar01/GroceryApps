package com.sokhal.groceryapp.domain.use_case.auth

import com.sokhal.groceryapp.domain.model.User
import com.sokhal.groceryapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        name: String? = null,
        email: String? = null,
        profilePicture: String? = null
    ): Flow<Result<User>> {
        // At least one field should be provided for update
        if (name == null && email == null && profilePicture == null) {
            return flow {
                emit(Result.failure(Exception("At least one field must be provided for update")))
            }
        }
        
        // Validate email if provided
        if (email != null && email.isNotBlank() && !isValidEmail(email)) {
            return flow {
                emit(Result.failure(Exception("Invalid email format")))
            }
        }
        
        // Validate name if provided
        if (name != null && name.isBlank()) {
            return flow {
                emit(Result.failure(Exception("Name cannot be empty if provided")))
            }
        }
        
        return repository.updateUser(name, email, profilePicture)
    }
    
    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailRegex.toRegex())
    }
}