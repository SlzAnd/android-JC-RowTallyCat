package com.example.counterjc.logic

import com.example.counterjc.data.Product

sealed interface ProductEvent {
    data object SaveProduct: ProductEvent
    data class SetProductName(val name: String): ProductEvent
    data object ShowDialog: ProductEvent
    data object HideDialog: ProductEvent
    data class DeleteProduct(val product: Product): ProductEvent
    data class SortProducts(val sortType: SortType): ProductEvent
}