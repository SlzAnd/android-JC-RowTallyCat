package com.example.counterjc.feature_counter.domain.use_case

import com.example.counterjc.feature_counter.domain.model.Product
import com.example.counterjc.feature_counter.domain.repository.ProductRepository

class DeleteProduct(
    private val repository: ProductRepository
) {

    suspend operator fun invoke(product: Product) {
        repository.deleteProduct(product)
    }
}