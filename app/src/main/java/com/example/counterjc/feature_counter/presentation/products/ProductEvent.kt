package com.example.counterjc.feature_counter.presentation.products

import com.example.counterjc.feature_counter.domain.model.Product
import com.example.counterjc.feature_counter.domain.util.SortType

sealed interface ProductEvent {
    data object SaveProduct: ProductEvent
    data class SetProductName(val name: String): ProductEvent
    data class SetGoal(val goal: Int): ProductEvent
    data object ShowDialog: ProductEvent
    data object HideDialog: ProductEvent
    data object ShowErrorMessage: ProductEvent
    data object HideErrorMessage: ProductEvent
    data class DeleteProduct(val product: Product): ProductEvent
    data class SortProducts(val sortType: SortType): ProductEvent
    data object RestoreProduct: ProductEvent
}