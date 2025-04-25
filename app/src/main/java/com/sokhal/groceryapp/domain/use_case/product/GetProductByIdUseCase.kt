package com.sokhal.groceryapp.domain.use_case.product

import com.sokhal.groceryapp.domain.model.Product
import com.sokhal.groceryapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: String): Flow<Result<Product>> {
        if (id.isBlank()) {
            return flow {
                emit(Result.failure(Exception("Product ID cannot be empty")))
            }
        }
        
        return repository.getProductById(id)
    }
}