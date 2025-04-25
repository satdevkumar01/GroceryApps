package com.sokhal.groceryapp.domain.use_case.auth

import com.sokhal.groceryapp.domain.model.User
import com.sokhal.groceryapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Result<Pair<String, User>>> {
        if (email.isBlank()) {
            return kotlinx.coroutines.flow.flow {
                emit(Result.failure(Exception("Email cannot be empty")))
            }
        }
        if (password.isBlank()) {
            return kotlinx.coroutines.flow.flow {
                emit(Result.failure(Exception("Password cannot be empty")))
            }
        }
        return repository.login(email, password)
    }
}