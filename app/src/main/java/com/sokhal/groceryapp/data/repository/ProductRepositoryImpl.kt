package com.sokhal.groceryapp.data.repository

import com.sokhal.groceryapp.data.remote.api.ProductApi
import com.sokhal.groceryapp.data.remote.dto.AddProductRequest
import com.sokhal.groceryapp.data.remote.dto.UpdateProductRequest
import com.sokhal.groceryapp.domain.model.Product
import com.sokhal.groceryapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApi
) : ProductRepository {

    override suspend fun getAllProducts(): Flow<Result<List<Product>>> = flow {
        try {
            val response = productApi.getAllProducts()
            val products = response.map { productMap ->
                mapToProduct(productMap)
            }
            emit(Result.success(products))
        } catch (e: HttpException) {
            emit(Result.failure(Exception("An error occurred: ${e.message()}")))
        } catch (e: IOException) {
            emit(Result.failure(Exception("Couldn't reach server. Check your internet connection.")))
        } catch (e: Exception) {
            emit(Result.failure(Exception("An unexpected error occurred: ${e.message}")))
        }
    }

    override suspend fun getProductById(id: String): Flow<Result<Product>> = flow {
        try {
            val response = productApi.getProductById(id)
            val product = mapToProduct(response)
            emit(Result.success(product))
        } catch (e: HttpException) {
            emit(Result.failure(Exception("An error occurred: ${e.message()}")))
        } catch (e: IOException) {
            emit(Result.failure(Exception("Couldn't reach server. Check your internet connection.")))
        } catch (e: Exception) {
            emit(Result.failure(Exception("An unexpected error occurred: ${e.message}")))
        }
    }

    override suspend fun addProduct(
        name: String,
        description: String,
        price: Double,
        imageUrl: String?,
        category: String,
        quantity: Int
    ): Flow<Result<Product>> = flow {
        try {
            val response = productApi.addProduct(
                AddProductRequest(
                    name = name,
                    description = description,
                    price = price,
                    imageUrl = imageUrl,
                    category = category,
                    quantity = quantity
                ).toMap()
            )
            val product = mapToProduct(response)
            emit(Result.success(product))
        } catch (e: HttpException) {
            emit(Result.failure(Exception("An error occurred: ${e.message()}")))
        } catch (e: IOException) {
            emit(Result.failure(Exception("Couldn't reach server. Check your internet connection.")))
        } catch (e: Exception) {
            emit(Result.failure(Exception("An unexpected error occurred: ${e.message}")))
        }
    }

    override suspend fun updateProduct(
        id: String,
        name: String?,
        description: String?,
        price: Double?,
        imageUrl: String?,
        category: String?,
        quantity: Int?
    ): Flow<Result<Product>> = flow {
        try {
            val response = productApi.updateProduct(
                id,
                UpdateProductRequest(
                    name = name,
                    description = description,
                    price = price,
                    imageUrl = imageUrl,
                    category = category,
                    quantity = quantity
                ).toMap()
            )
            val product = mapToProduct(response)
            emit(Result.success(product))
        } catch (e: HttpException) {
            emit(Result.failure(Exception("An error occurred: ${e.message()}")))
        } catch (e: IOException) {
            emit(Result.failure(Exception("Couldn't reach server. Check your internet connection.")))
        } catch (e: Exception) {
            emit(Result.failure(Exception("An unexpected error occurred: ${e.message}")))
        }
    }

    override suspend fun deleteProduct(id: String): Flow<Result<Boolean>> = flow {
        try {
            productApi.deleteProduct(id)
            emit(Result.success(true))
        } catch (e: HttpException) {
            emit(Result.failure(Exception("An error occurred: ${e.message()}")))
        } catch (e: IOException) {
            emit(Result.failure(Exception("Couldn't reach server. Check your internet connection.")))
        } catch (e: Exception) {
            emit(Result.failure(Exception("An unexpected error occurred: ${e.message}")))
        }
    }

    private fun mapToProduct(productMap: Map<String, Any>): Product {
        return Product(
            id = productMap["id"] as String,
            name = productMap["name"] as String,
            description = productMap["description"] as String,
            price = (productMap["price"] as Number).toDouble(),
            imageUrl = productMap["image_url"] as? String,
            category = productMap["category"] as String,
            quantity = (productMap["quantity"] as Number).toInt(),
            createdAt = productMap["created_at"] as String,
            updatedAt = productMap["updated_at"] as String
        )
    }

    // Extension functions to convert DTOs to Maps
    private fun AddProductRequest.toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["name"] = name
        map["description"] = description
        map["price"] = price
        map["category"] = category
        map["quantity"] = quantity
        imageUrl?.let { map["image_url"] = it }
        return map
    }

    private fun UpdateProductRequest.toMap(): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        name?.let { map["name"] = it }
        description?.let { map["description"] = it }
        price?.let { map["price"] = it }
        category?.let { map["category"] = it }
        quantity?.let { map["quantity"] = it }
        imageUrl?.let { map["image_url"] = it }
        return map
    }
}