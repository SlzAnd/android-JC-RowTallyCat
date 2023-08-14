package com.example.counterjc.feature_counter.domain.use_case

data class ProductUseCases(
    val getProducts: GetProducts,
    val deleteProduct: DeleteProduct,
    val addProduct: AddProduct,
    val getProduct: GetProduct,
)