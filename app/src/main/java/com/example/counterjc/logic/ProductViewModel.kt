package com.example.counterjc.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.counterjc.data.Product
import com.example.counterjc.data.ProductDAO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel(
    private val dao: ProductDAO
): ViewModel() {

    private val _sortType = MutableStateFlow(SortType.MODIFIED_TIME)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _products = _sortType
        .flatMapLatest {sortType ->
            when(sortType) {
                SortType.NAME -> {
                    dao.getProductsOrderedByName()
                }
                SortType.MODIFIED_TIME -> {
                    dao.getProductsOrderedByModificationTime()
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(ProductState())

    val state = combine(_state, _sortType, _products) { state, sortType, products ->
        state.copy(
            products = products,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductState())
    fun onEvent(event: ProductEvent) {
        when(event) {
            is ProductEvent.DeleteProduct -> {
                viewModelScope.launch {
                    dao.deleteProduct(event.product)
                }
            }

            ProductEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingProduct = false
                    )
                }
            }

            ProductEvent.SaveProduct -> {
                val productName = state.value.name

                if (productName.isBlank()) {
                    return
                }

                val product = Product(
                    name = productName,
                    modifiedAt = System.currentTimeMillis()
                )

                viewModelScope.launch {
                    dao.upsertProduct(product)
                }

                _state.update {
                    it.copy(
                        isAddingProduct = false,
                        name = ""
                    )
                }
            }

            is ProductEvent.SetProductName -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }

            ProductEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingProduct = true
                    )
                }
            }

            is ProductEvent.SortProducts -> {
                _sortType.value = event.sortType
            }
        }
    }
}