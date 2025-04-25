package com.sokhal.groceryapp.data.remote.dto

import com.google.gson.annotations.SerializedName

// Request DTOs
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class ForgotPasswordRequest(
    val email: String
)

data class ResetPasswordRequest(
    val token: String,
    val password: String
)

data class UpdateUserRequest(
    val name: String? = null,
    val email: String? = null,
    @SerializedName("profile_picture")
    val profilePicture: String? = null
)

// Response DTOs
data class AuthResponse(
    val token: String,
    val user: UserDto
)

data class UserDto(
    val id: String,
    val name: String,
    val email: String,
    @SerializedName("profile_picture")
    val profilePicture: String? = null
)

data class MessageResponse(
    val message: String
)