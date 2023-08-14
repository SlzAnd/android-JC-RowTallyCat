package com.example.counterjc.feature_counter.domain.use_case

import com.example.counterjc.feature_counter.domain.model.InvalidProductException
import com.example.counterjc.feature_counter.domain.model.Product
import com.example.counterjc.feature_counter.domain.repository.ProductRepository
import kotlin.jvm.Throws

class AddProduct(
    private val repository: ProductRepository
) {

    @Throws(InvalidProductException::class)
    suspend operator fun invoke(product: Product) {
        if (product.name.isBlank()) {
            throw InvalidProductException("The name of the product can't be empty")
        }
        repository.upsertProduct(product)
    }
}