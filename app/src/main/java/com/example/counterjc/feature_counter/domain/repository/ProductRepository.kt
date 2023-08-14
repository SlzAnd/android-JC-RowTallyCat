package com.example.counterjc.feature_counter.domain.repository

import com.example.counterjc.feature_counter.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProductsOrderedByModificationTime(): Flow<List<Product>>
    fun getProductsOrderedByName(): Flow<List<Product>>

    suspend fun getProductById(id: Int): Product?

    suspend fun upsertProduct(product: Product)

    suspend fun deleteProduct(product: Product)
}