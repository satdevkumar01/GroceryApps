package com.sokhal.groceryapp.data.repository

import com.sokhal.groceryapp.data.local.PreferencesManager
import com.sokhal.groceryapp.data.local.dao.UserDao
import com.sokhal.groceryapp.data.local.entity.UserEntity
import com.sokhal.groceryapp.data.remote.api.AuthApi
import com.sokhal.groceryapp.data.remote.dto.ForgotPasswordRequest
import com.sokhal.groceryapp.data.remote.dto.LoginRequest
import com.sokhal.groceryapp.data.remote.dto.RegisterRequest
import com.sokhal.groceryapp.data.remote.dto.ResetPasswordRequest
import com.sokhal.groceryapp.data.remote.dto.UpdateUserRequest
import com.sokhal.groceryapp.data.remote.dto.UserDto
import com.sokhal.groceryapp.domain.model.User
import com.sokhal.groceryapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val preferencesManager: PreferencesManager,
    private val userDao: UserDao
) : AuthRepository {

    private var currentUser: User? = null

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Flow<Result<Pair<String, User>>> = flow {
        try {
            val response = authApi.register(
                RegisterRequest(
                    name = name,
                    email = email,
                    password = password
                ).toMap()
            )

            val token = response["token"] as String
            val userMap = response["user"] as Map<*, *>

            val id = userMap["id"] as Double
            val user = User(
                id = id.toString(),
                name = userMap["name"] as String,
                email = userMap["email"] as String,
                profilePicture = userMap["profile_picture"] as? String
            )

            saveAuthToken(token)
            currentUser = user

            // Save user to database
            userDao.insertUser(UserEntity.fromUser(user))

            emit(Result.success(Pair(token, user)))
        } catch (e: HttpException) {
            emit(Result.failure(Exception("An error occurred: ${e.message()}")))
        } catch (e: IOException) {
            emit(Result.failure(Exception("Couldn't reach server. Check your internet connection.")))
        } catch (e: Exception) {
            emit(Result.failure(Exception("An unexpected error occurred: ${e.message}")))
        }
    }

    override suspend fun login(email: String, password: String): Flow<Result<Pair<String, User>>> = flow {
        try {
            val response = authApi.login(
                LoginRequest(
                    email = email,
                    password = password
                ).toMap()
            )

            val token = response["token"] as String
            val userMap = response["user"] as Map<*, *>
            val id = userMap["id"] as Double

            val user = User(
                id = id.toString(),
                name = userMap["name"] as String,
                email = userMap["email"] as String,
                profilePicture = userMap["profile_picture"] as? String
            )

            saveAuthToken(token)
            currentUser = user

            // Save user to database
            userDao.insertUser(UserEntity.fromUser(user))

            emit(Result.success(Pair(token, user)))
        } catch (e: HttpException) {
            emit(Result.failure(Exception("An error occurred: ${e.message()}")))
        } catch (e: IOException) {
            emit(Result.failure(Exception("Couldn't reach server. Check your internet connection.")))
        } catch (e: Exception) {
            emit(Result.failure(Exception("An unexpected error occurred: ${e.message}")))
        }
    }

    override suspend fun forgotPassword(email: String): Flow<Result<String>> = flow {
        try {
            val response = authApi.forgotPassword(
                ForgotPasswordRequest(
                    email = email
                ).toMap()
            )

            val message = response["message"] as String
            emit(Result.success(message))
        } catch (e: HttpException) {
            emit(Result.failure(Exception("An error occurred: ${e.message()}")))
        } catch (e: IOException) {
            emit(Result.failure(Exception("Couldn't reach server. Check your internet connection.")))
        } catch (e: Exception) {
            emit(Result.failure(Exception("An unexpected error occurred: ${e.message}")))
        }
    }

    override suspend fun resetPassword(token: String, password: String): Flow<Result<String>> = flow {
        try {
            val response = authApi.resetPassword(
                ResetPasswordRequest(
                    token = token,
                    password = password
                ).toMap()
            )

            val message = response["message"] as String
            emit(Result.success(message))
        } catch (e: HttpException) {
            emit(Result.failure(Exception("An error occurred: ${e.message()}")))
        } catch (e: IOException) {
            emit(Result.failure(Exception("Couldn't reach server. Check your internet connection.")))
        } catch (e: Exception) {
            emit(Result.failure(Exception("An unexpected error occurred: ${e.message}")))
        }
    }

    override suspend fun updateUser(
        name: String?,
        email: String?,
        profilePicture: String?
    ): Flow<Result<User>> = flow {
        try {
            val response = authApi.updateUser(
                UpdateUserRequest(
                    name = name,
                    email = email,
                    profilePicture = profilePicture
                ).toMap()
            )

            val userMap = response["user"] as Map<*, *>

            val user = User(
                id = userMap["id"] as String,
                name = userMap["name"] as String,
                email = userMap["email"] as String,
                profilePicture = userMap["profile_picture"] as? String
            )

            currentUser = user

            // Update user in database
            userDao.insertUser(UserEntity.fromUser(user))

            emit(Result.success(user))
        } catch (e: HttpException) {
            emit(Result.failure(Exception("An error occurred: ${e.message()}")))
        } catch (e: IOException) {
            emit(Result.failure(Exception("Couldn't reach server. Check your internet connection.")))
        } catch (e: Exception) {
            emit(Result.failure(Exception("An unexpected error occurred: ${e.message}")))
        }
    }

    override suspend fun getCurrentUser(): User? {
        if (currentUser == null) {
            // Try to get user from database
            val userEntity = userDao.getUser()
            currentUser = userEntity?.toUser()
        }
        return currentUser
    }

    override suspend fun saveAuthToken(token: String) {
        preferencesManager.saveAuthToken(token)
    }

    override suspend fun getAuthToken(): String? {
        return preferencesManager.getAuthToken()
    }

    override suspend fun clearAuthToken() {
        preferencesManager.clearAuthToken()
        currentUser = null
        // Clear user from database
        userDao.deleteAllUsers()
    }

    // Extension functions to convert DTOs to Maps
    private fun RegisterRequest.toMap(): Map<String, String> {
        return mapOf(
            "name" to name,
            "email" to email,
            "password" to password
        )
    }

    private fun LoginRequest.toMap(): Map<String, String> {
        return mapOf(
            "email" to email,
            "password" to password
        )
    }

    private fun ForgotPasswordRequest.toMap(): Map<String, String> {
        return mapOf(
            "email" to email
        )
    }

    private fun ResetPasswordRequest.toMap(): Map<String, String> {
        return mapOf(
            "token" to token,
            "password" to password
        )
    }

    private fun UpdateUserRequest.toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        name?.let { map["name"] = it }
        email?.let { map["email"] = it }
        profilePicture?.let { map["profile_picture"] = it }
        return map
    }
}
