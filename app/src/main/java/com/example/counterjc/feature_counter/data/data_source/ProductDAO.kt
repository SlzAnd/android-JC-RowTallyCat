package com.example.counterjc.feature_counter.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.counterjc.feature_counter.domain.model.Product
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

    @Query("SELECT * FROM product WHERE product.id = :id")
    suspend fun getProductById(id: Int): Product?

    @Query("UPDATE product SET currentCounterValue = :counterValue WHERE id = :productId")
    suspend fun updateProductCounterValue(productId: Int, counterValue: Int)
}