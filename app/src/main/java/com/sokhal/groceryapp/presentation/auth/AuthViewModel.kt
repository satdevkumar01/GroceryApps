package com.sokhal.groceryapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sokhal.groceryapp.domain.model.User
import com.sokhal.groceryapp.domain.use_case.auth.ForgotPasswordUseCase
import com.sokhal.groceryapp.domain.use_case.auth.LoginUseCase
import com.sokhal.groceryapp.domain.use_case.auth.RegisterUseCase
import com.sokhal.groceryapp.domain.use_case.auth.ResetPasswordUseCase
import com.sokhal.groceryapp.domain.use_case.auth.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun login(email: String, password: String) {
        _authState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            loginUseCase(email, password).collect { result ->
                result.fold(
                    onSuccess = { (token, user) ->
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                isAuthenticated = true,
                                user = user,
                                error = null
                            )
                        }
                    },
                    onFailure = { error ->
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                error = error.message ?: "An unknown error occurred"
                            )
                        }
                    }
                )
            }
        }
    }

    fun register(name: String, email: String, password: String) {
        _authState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            registerUseCase(name, email, password).collect { result ->
                result.fold(
                    onSuccess = { (token, user) ->
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                isAuthenticated = true,
                                user = user,
                                error = null
                            )
                        }
                    },
                    onFailure = { error ->
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                error = error.message ?: "An unknown error occurred"
                            )
                        }
                    }
                )
            }
        }
    }

    fun forgotPassword(email: String) {
        _authState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            forgotPasswordUseCase(email).collect { result ->
                result.fold(
                    onSuccess = { message ->
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                message = message,
                                error = null
                            )
                        }
                    },
                    onFailure = { error ->
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                error = error.message ?: "An unknown error occurred"
                            )
                        }
                    }
                )
            }
        }
    }

    fun resetPassword(token: String, password: String) {
        _authState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            resetPasswordUseCase(token, password).collect { result ->
                result.fold(
                    onSuccess = { message ->
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                message = message,
                                error = null
                            )
                        }
                    },
                    onFailure = { error ->
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                error = error.message ?: "An unknown error occurred"
                            )
                        }
                    }
                )
            }
        }
    }

    fun updateUser(name: String? = null, email: String? = null, profilePicture: String? = null) {
        _authState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            updateUserUseCase(name, email, profilePicture).collect { result ->
                result.fold(
                    onSuccess = { user ->
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                user = user,
                                error = null
                            )
                        }
                    },
                    onFailure = { error ->
                        _authState.update {
                            it.copy(
                                isLoading = false,
                                error = error.message ?: "An unknown error occurred"
                            )
                        }
                    }
                )
            }
        }
    }

    fun clearError() {
        _authState.update { it.copy(error = null) }
    }

    fun clearMessage() {
        _authState.update { it.copy(message = null) }
    }
}

data class AuthState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val user: User? = null,
    val error: String? = null,
    val message: String? = null
)