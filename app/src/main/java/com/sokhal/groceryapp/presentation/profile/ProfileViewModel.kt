package com.sokhal.groceryapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sokhal.groceryapp.domain.model.User
import com.sokhal.groceryapp.domain.repository.AuthRepository
import com.sokhal.groceryapp.domain.use_case.auth.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        _profileState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                val user = authRepository.getCurrentUser()
                _profileState.update {
                    it.copy(
                        isLoading = false,
                        user = user,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _profileState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "An unknown error occurred"
                    )
                }
            }
        }
    }

    fun updateProfile(name: String? = null, email: String? = null, profilePicture: String? = null) {
        _profileState.update { it.copy(isLoading = true, error = null, isUpdateSuccessful = null) }
        viewModelScope.launch {
            updateUserUseCase(name, email, profilePicture).collect { result ->
                result.fold(
                    onSuccess = { user ->
                        _profileState.update {
                            it.copy(
                                isLoading = false,
                                user = user,
                                error = null,
                                isUpdateSuccessful = true
                            )
                        }
                    },
                    onFailure = { error ->
                        _profileState.update {
                            it.copy(
                                isLoading = false,
                                error = error.message ?: "An unknown error occurred",
                                isUpdateSuccessful = false
                            )
                        }
                    }
                )
            }
        }
    }

    fun logout() {
        _profileState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                authRepository.clearAuthToken()
                _profileState.update {
                    it.copy(
                        isLoading = false,
                        isLoggedOut = true,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _profileState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "An unknown error occurred"
                    )
                }
            }
        }
    }

    fun clearError() {
        _profileState.update { it.copy(error = null) }
    }

    fun resetUpdateStatus() {
        _profileState.update { it.copy(isUpdateSuccessful = null) }
    }
}

data class ProfileState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null,
    val isUpdateSuccessful: Boolean? = null,
    val isLoggedOut: Boolean = false
)