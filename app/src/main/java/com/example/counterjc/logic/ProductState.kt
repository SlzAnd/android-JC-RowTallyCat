package com.example.counterjc.logic

import com.example.counterjc.data.Product

data class ProductState(
    val products: List<Product> = emptyList(),
    val name: String = "",
    val isAddingProduct: Boolean = false,
    val sortType: SortType = SortType.MODIFIED_TIME

)
