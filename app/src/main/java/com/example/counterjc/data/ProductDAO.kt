package com.example.counterjc.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Upsert

@Dao
interface ProductDAO {

    @Upsert
    suspend fun upsertProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    

}