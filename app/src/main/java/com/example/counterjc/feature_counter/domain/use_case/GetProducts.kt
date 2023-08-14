package com.example.counterjc.feature_counter.domain.use_case

import com.example.counterjc.feature_counter.domain.model.Product
import com.example.counterjc.feature_counter.domain.repository.ProductRepository
import com.example.counterjc.feature_counter.domain.util.SortType
import kotlinx.coroutines.flow.Flow

class GetProducts(
    private val repository: ProductRepository
) {

    operator fun invoke(sortType: SortType = SortType.MODIFIED_TIME): Flow<List<Product>> {
        return when (sortType) {
            SortType.NAME -> {
                repository.getProductsOrderedByName()
            }

            SortType.MODIFIED_TIME -> {
                repository.getProductsOrderedByModificationTime()
            }
        }
    }
}