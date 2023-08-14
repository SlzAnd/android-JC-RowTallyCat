package com.example.counterjc.feature_counter.data.repository

import com.example.counterjc.feature_counter.data.data_source.ProductDAO
import com.example.counterjc.feature_counter.domain.model.Product
import com.example.counterjc.feature_counter.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(
    private val dao: ProductDAO
): ProductRepository {

    override fun getProductsOrderedByModificationTime(): Flow<List<Product>> {
        return dao.getProductsOrderedByModificationTime()
    }

    override fun getProductsOrderedByName(): Flow<List<Product>> {
        return dao.getProductsOrderedByName()
    }

    override suspend fun getProductById(id: Int): Product? {
        return dao.getProductById(id)
    }

    override suspend fun upsertProduct(product: Product) {
        dao.upsertProduct(product)
    }

    override suspend fun deleteProduct(product: Product) {
        dao.deleteProduct(product)
    }
}