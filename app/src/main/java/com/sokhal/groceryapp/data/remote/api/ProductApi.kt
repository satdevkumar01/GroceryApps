package com.sokhal.groceryapp.data.remote.api

import retrofit2.http.*

interface ProductApi {
    
    @GET("/products")
    suspend fun getAllProducts(): List<Map<String, Any>>
    
    @POST("/addproducts")
    suspend fun addProduct(@Body productRequest: Map<String, Any>): Map<String, Any>
    
    @PUT("/products/{id}")
    suspend fun updateProduct(
        @Path("id") productId: String,
        @Body productRequest: Map<String, Any>
    ): Map<String, Any>
    
    @DELETE("/products/{id}")
    suspend fun deleteProduct(@Path("id") productId: String): Map<String, Any>
    
    @GET("/products/{id}")
    suspend fun getProductById(@Path("id") productId: String): Map<String, Any>
}