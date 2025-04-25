package com.sokhal.groceryapp.data.remote.dto

import com.google.gson.annotations.SerializedName

// Request DTOs
data class AddProductRequest(
    val name: String,
    val description: String,
    val price: Double,
    @SerializedName("image_url")
    val imageUrl: String? = null,
    val category: String,
    val quantity: Int
)

data class UpdateProductRequest(
    val name: String? = null,
    val description: String? = null,
    val price: Double? = null,
    @SerializedName("image_url")
    val imageUrl: String? = null,
    val category: String? = null,
    val quantity: Int? = null
)

// Response DTOs
data class ProductDto(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    @SerializedName("image_url")
    val imageUrl: String? = null,
    val category: String,
    val quantity: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)

data class ProductsResponse(
    val products: List<ProductDto>
)

data class ProductResponse(
    val product: ProductDto
)