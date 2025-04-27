package com.sokhal.groceryapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sokhal.groceryapp.domain.model.Product
import com.sokhal.groceryapp.domain.use_case.product.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> = _homeState.asStateFlow()

    init {
        loadFeaturedProducts()
    }

    fun loadFeaturedProducts() {
        _homeState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            getAllProductsUseCase().collect { result ->
                result.fold(
                    onSuccess = { allProducts ->
                        // For now, we'll just use all products as featured products
                        // In a real app, you might have a specific API for featured products
                        // or filter them based on some criteria
                        val featuredProducts = allProducts.take(5)
                        
                        // Group products by category
                        val productsByCategory = allProducts.groupBy { it.category }
                        
                        _homeState.update {
                            it.copy(
                                isLoading = false,
                                featuredProducts = featuredProducts,
                                categories = productsByCategory.keys.toList(),
                                productsByCategory = productsByCategory,
                                error = null
                            )
                        }
                    },
                    onFailure = { error ->
                        _homeState.update {
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

    fun refreshHomeData() {
        loadFeaturedProducts()
    }

    fun clearError() {
        _homeState.update { it.copy(error = null) }
    }
}

data class HomeState(
    val isLoading: Boolean = false,
    val featuredProducts: List<Product> = emptyList(),
    val categories: List<String> = emptyList(),
    val productsByCategory: Map<String, List<Product>> = emptyMap(),
    val error: String? = null
)