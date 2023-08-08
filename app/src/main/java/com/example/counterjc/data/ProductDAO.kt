package com.example.counterjc.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDAO {

    @Upsert
    suspend fun upsertProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("SELECT * FROM product ORDER BY name ASC")
    fun getProductsOrderedByName(): Flow<List<Product>>

    @Query("SELECT * FROM product ORDER BY modified_at DESC")
    fun getProductsOrderedByModificationTime(): Flow<List<Product>>
}