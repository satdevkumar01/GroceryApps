package com.sokhal.groceryapp.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sokhal.groceryapp.domain.model.Product
import com.sokhal.groceryapp.domain.use_case.product.AddProductUseCase
import com.sokhal.groceryapp.domain.use_case.product.DeleteProductUseCase
import com.sokhal.groceryapp.domain.use_case.product.GetAllProductsUseCase
import com.sokhal.groceryapp.domain.use_case.product.GetProductByIdUseCase
import com.sokhal.groceryapp.domain.use_case.product.UpdateProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase
) : ViewModel() {

    private val _productListState = MutableStateFlow(ProductListState())
    val productListState: StateFlow<ProductListState> = _productListState.asStateFlow()

    private val _productDetailState = MutableStateFlow(ProductDetailState())
    val productDetailState: StateFlow<ProductDetailState> = _productDetailState.asStateFlow()

    fun getAllProducts() {
        _productListState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            getAllProductsUseCase().collect { result ->
                result.fold(
                    onSuccess = { products ->
                        _productListState.update {
                            it.copy(
                                isLoading = false,
                                products = products,
                                error = null
                            )
                        }
                    },
                    onFailure = { error ->
                        _productListState.update {
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

    fun getProductById(id: String) {
        _productDetailState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            getProductByIdUseCase(id).collect { result ->
                result.fold(
                    onSuccess = { product ->
                        _productDetailState.update {
                            it.copy(
                                isLoading = false,
                                product = product,
                                error = null
                            )
                        }
                    },
                    onFailure = { error ->
                        _productDetailState.update {
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

    fun addProduct(
        name: String,
        description: String,
        price: Double,
        imageUrl: String?,
        category: String,
        quantity: Int
    ) {
        _productDetailState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            addProductUseCase(name, description, price, imageUrl, category, quantity).collect { result ->
                result.fold(
                    onSuccess = { product ->
                        _productDetailState.update {
                            it.copy(
                                isLoading = false,
                                product = product,
                                error = null,
                                isOperationSuccessful = true
                            )
                        }
                        // Refresh the product list
                        getAllProducts()
                    },
                    onFailure = { error ->
                        _productDetailState.update {
                            it.copy(
                                isLoading = false,
                                error = error.message ?: "An unknown error occurred",
                                isOperationSuccessful = false
                            )
                        }
                    }
                )
            }
        }
    }

    fun updateProduct(
        id: String,
        name: String? = null,
        description: String? = null,
        price: Double? = null,
        imageUrl: String? = null,
        category: String? = null,
        quantity: Int? = null
    ) {
        _productDetailState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            updateProductUseCase(id, name, description, price, imageUrl, category, quantity).collect { result ->
                result.fold(
                    onSuccess = { product ->
                        _productDetailState.update {
                            it.copy(
                                isLoading = false,
                                product = product,
                                error = null,
                                isOperationSuccessful = true
                            )
                        }
                        // Refresh the product list
                        getAllProducts()
                    },
                    onFailure = { error ->
                        _productDetailState.update {
                            it.copy(
                                isLoading = false,
                                error = error.message ?: "An unknown error occurred",
                                isOperationSuccessful = false
                            )
                        }
                    }
                )
            }
        }
    }

    fun deleteProduct(id: String) {
        _productDetailState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            deleteProductUseCase(id).collect { result ->
                result.fold(
                    onSuccess = { success ->
                        _productDetailState.update {
                            it.copy(
                                isLoading = false,
                                product = null,
                                error = null,
                                isOperationSuccessful = success
                            )
                        }
                        // Refresh the product list
                        getAllProducts()
                    },
                    onFailure = { error ->
                        _productDetailState.update {
                            it.copy(
                                isLoading = false,
                                error = error.message ?: "An unknown error occurred",
                                isOperationSuccessful = false
                            )
                        }
                    }
                )
            }
        }
    }

    fun resetOperationStatus() {
        _productDetailState.update { it.copy(isOperationSuccessful = null) }
    }

    fun clearProductDetailError() {
        _productDetailState.update { it.copy(error = null) }
    }

    fun clearProductListError() {
        _productListState.update { it.copy(error = null) }
    }
}

data class ProductListState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String? = null
)

data class ProductDetailState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val error: String? = null,
    val isOperationSuccessful: Boolean? = null
)