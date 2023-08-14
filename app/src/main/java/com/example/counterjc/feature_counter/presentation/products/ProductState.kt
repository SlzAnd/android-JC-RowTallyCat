package com.example.counterjc.feature_counter.presentation.products

import com.example.counterjc.feature_counter.domain.model.Product
import com.example.counterjc.feature_counter.domain.util.SortType

data class ProductState(
    val products: List<Product> = emptyList(),
    val name: String = "",
    val goal: Int = 0,
    val isAddingProduct: Boolean = false,
    val sortType: SortType = SortType.MODIFIED_TIME,
    val currentCounterValue: Int = 0,
    val isShowErrorMessage: Boolean = false,

)
