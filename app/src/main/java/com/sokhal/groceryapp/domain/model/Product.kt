package com.sokhal.groceryapp.domain.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String? = null,
    val category: String,
    val quantity: Int,
    val createdAt: String,
    val updatedAt: String
)