package com.sokhal.groceryapp.domain.use_case.product

import com.sokhal.groceryapp.domain.model.Product
import com.sokhal.groceryapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(
        name: String,
        description: String,
        price: Double,
        imageUrl: String?,
        category: String,
        quantity: Int
    ): Flow<Result<Product>> {
        // Validate name
        if (name.isBlank()) {
            return flow {
                emit(Result.failure(Exception("Product name cannot be empty")))
            }
        }
        
        // Validate description
        if (description.isBlank()) {
            return flow {
                emit(Result.failure(Exception("Product description cannot be empty")))
            }
        }
        
        // Validate price
        if (price <= 0) {
            return flow {
                emit(Result.failure(Exception("Product price must be greater than zero")))
            }
        }
        
        // Validate category
        if (category.isBlank()) {
            return flow {
                emit(Result.failure(Exception("Product category cannot be empty")))
            }
        }
        
        // Validate quantity
        if (quantity < 0) {
            return flow {
                emit(Result.failure(Exception("Product quantity cannot be negative")))
            }
        }
        
        return repository.addProduct(name, description, price, imageUrl, category, quantity)
    }
}