package com.sokhal.groceryapp.domain.use_case.product

import com.sokhal.groceryapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: String): Flow<Result<Boolean>> {
        if (id.isBlank()) {
            return flow {
                emit(Result.failure(Exception("Product ID cannot be empty")))
            }
        }
        
        return repository.deleteProduct(id)
    }
}