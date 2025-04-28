package com.sokhal.groceryapp.presentation.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sokhal.groceryapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _splashState = MutableStateFlow(SplashState())
    val splashState: StateFlow<SplashState> = _splashState.asStateFlow()

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        _splashState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                // Add a small delay to show the splash screen
                delay(1500)
                
                // Check if user is authenticated
                val token = authRepository.getAuthToken()
                val user = authRepository.getCurrentUser()

                Log.e("Data","User Token : $token  and User ${user?.id} , User is null : ${user !=null}")
                
                _splashState.update {
                    it.copy(
                        isLoading = false,
                        isAuthenticated = !token.isNullOrEmpty() && user != null,
                        isInitialized = true
                    )
                }
            } catch (e: Exception) {
                _splashState.update {
                    it.copy(
                        isLoading = false,
                        isAuthenticated = false,
                        isInitialized = true,
                        error = e.message
                    )
                }
            }
        }
    }
}

data class SplashState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val isInitialized: Boolean = false,
    val error: String? = null
)