package com.sokhal.groceryapp.domain.use_case.product

import com.sokhal.groceryapp.domain.model.Product
import com.sokhal.groceryapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(
        id: String,
        name: String? = null,
        description: String? = null,
        price: Double? = null,
        imageUrl: String? = null,
        category: String? = null,
        quantity: Int? = null
    ): Flow<Result<Product>> {
        // Validate ID
        if (id.isBlank()) {
            return flow {
                emit(Result.failure(Exception("Product ID cannot be empty")))
            }
        }
        
        // At least one field should be provided for update
        if (name == null && description == null && price == null && 
            imageUrl == null && category == null && quantity == null) {
            return flow {
                emit(Result.failure(Exception("At least one field must be provided for update")))
            }
        }
        
        // Validate name if provided
        if (name != null && name.isBlank()) {
            return flow {
                emit(Result.failure(Exception("Product name cannot be empty if provided")))
            }
        }
        
        // Validate description if provided
        if (description != null && description.isBlank()) {
            return flow {
                emit(Result.failure(Exception("Product description cannot be empty if provided")))
            }
        }
        
        // Validate price if provided
        if (price != null && price <= 0) {
            return flow {
                emit(Result.failure(Exception("Product price must be greater than zero if provided")))
            }
        }
        
        // Validate category if provided
        if (category != null && category.isBlank()) {
            return flow {
                emit(Result.failure(Exception("Product category cannot be empty if provided")))
            }
        }
        
        // Validate quantity if provided
        if (quantity != null && quantity < 0) {
            return flow {
                emit(Result.failure(Exception("Product quantity cannot be negative if provided")))
            }
        }
        
        return repository.updateProduct(id, name, description, price, imageUrl, category, quantity)
    }
}