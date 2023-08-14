package com.example.counterjc.feature_counter.presentation.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.counterjc.feature_counter.domain.model.Product
import com.example.counterjc.feature_counter.domain.use_case.ProductUseCases
import com.example.counterjc.feature_counter.domain.util.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productUseCases: ProductUseCases
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.MODIFIED_TIME)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _products = _sortType
        .flatMapLatest {sortType ->
            when(sortType) {
                SortType.NAME -> {
                    productUseCases.getProducts(SortType.NAME)
                }
                SortType.MODIFIED_TIME -> {
                    productUseCases.getProducts(SortType.MODIFIED_TIME)
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

//    private val _state = mutableStateOf(ProductsState())
//    val state: State<ProductsState> = _state

    private var recentlyDeletedProduct: Product? = null
    fun onEvent(event: ProductEvent) {
        when (event) {
            // Show Dialog Event
            ProductEvent.ShowDialog -> {
                _state.value = _state.value.copy(
                    isAddingProduct = true
                )
            }

            // Hide Dialog Event
            ProductEvent.HideDialog -> {
                _state.value = _state.value.copy(
                    isAddingProduct = false
                )
            }

            // Set Product Name Event
            is ProductEvent.SetProductName -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }

            // Set Goal Event
            is ProductEvent.SetGoal -> {
                _state.update {
                    it.copy(
                        goal = event.goal
                    )
                }
            }

            // Save Product Event
            ProductEvent.SaveProduct -> {
                val productName = state.value.name
                val goal = state.value.goal

                if (productName.isBlank()) {
                    return
                }

                val product = Product(
                    name = productName,
                    modifiedAt = System.currentTimeMillis(),
                    goalValue = goal
                )

                viewModelScope.launch {
                    productUseCases.addProduct(product)
                }

                _state.update {
                    it.copy(
                        isAddingProduct = false,
                        name = "",
                        goal = 0
                    )
                }
            }

            // Delete Product Event
            is ProductEvent.DeleteProduct -> {
                viewModelScope.launch {
                    productUseCases.deleteProduct(event.product)
                    recentlyDeletedProduct = event.product
                }
            }

            // Restore Product Event
            ProductEvent.RestoreProduct -> {
                viewModelScope.launch {
                    productUseCases.addProduct(recentlyDeletedProduct ?: return@launch)
                    recentlyDeletedProduct = null
                }
            }

            // Sort Products Event
            is ProductEvent.SortProducts -> {
                _sortType.value = event.sortType
            }

            ProductEvent.ShowErrorMessage -> {
                _state.value = _state.value.copy(
                    isShowErrorMessage = true
                )
            }
            ProductEvent.HideErrorMessage -> {
                _state.value = _state.value.copy(
                    isShowErrorMessage = false
                )
            }
        }
    }

}