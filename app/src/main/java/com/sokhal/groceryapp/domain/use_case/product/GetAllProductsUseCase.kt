package com.sokhal.groceryapp.domain.use_case.product

import com.sokhal.groceryapp.domain.model.Product
import com.sokhal.groceryapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): Flow<Result<List<Product>>> {
        return repository.getAllProducts()
    }
}