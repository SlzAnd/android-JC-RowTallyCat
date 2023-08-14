package com.example.counterjc.feature_counter.domain.use_case

import com.example.counterjc.feature_counter.domain.model.Product
import com.example.counterjc.feature_counter.domain.repository.ProductRepository

class GetProduct(
    private val repository: ProductRepository
) {

    suspend operator fun invoke(id: Int): Product? {
        return repository.getProductById(id)
    }
}