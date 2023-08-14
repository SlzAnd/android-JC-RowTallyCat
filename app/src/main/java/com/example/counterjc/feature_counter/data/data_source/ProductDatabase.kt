package com.example.counterjc.feature_counter.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.counterjc.feature_counter.domain.model.Product

@Database(
    entities = [Product::class],
    version = 1
)
abstract class ProductDatabase: RoomDatabase() {
    abstract val dao: ProductDAO

    companion object {
        const val DATABASE_NAME = "products.db"
    }
}