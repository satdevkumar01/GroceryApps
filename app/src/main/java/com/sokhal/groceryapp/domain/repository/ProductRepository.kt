package com.sokhal.groceryapp.domain.repository

import com.sokhal.groceryapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getAllProducts(): Flow<Result<List<Product>>>
    
    suspend fun getProductById(id: String): Flow<Result<Product>>
    
    suspend fun addProduct(
        name: String,
        description: String,
        price: Double,
        imageUrl: String?,
        category: String,
        quantity: Int
    ): Flow<Result<Product>>
    
    suspend fun updateProduct(
        id: String,
        name: String? = null,
        description: String? = null,
        price: Double? = null,
        imageUrl: String? = null,
        category: String? = null,
        quantity: Int? = null
    ): Flow<Result<Product>>
    
    suspend fun deleteProduct(id: String): Flow<Result<Boolean>>
}