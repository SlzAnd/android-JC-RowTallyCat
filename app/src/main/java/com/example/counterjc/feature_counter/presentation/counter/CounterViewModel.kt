package com.example.counterjc.feature_counter.presentation.counter

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.counterjc.R
import com.example.counterjc.feature_counter.domain.model.Product
import com.example.counterjc.feature_counter.domain.use_case.LoadBackgroundImage
import com.example.counterjc.feature_counter.domain.use_case.ProductUseCases
import com.example.counterjc.feature_counter.presentation.datastore.StoreSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CounterViewModel @Inject constructor(
    private val storeSettings: StoreSettings,
    private val productUseCases: ProductUseCases,
    private val loadBackgroundImage: LoadBackgroundImage,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var currentProductId: Int? = null

    private val _counterState = mutableStateOf(CounterState())
    val counterState: State<CounterState> = _counterState


    init {
        savedStateHandle.get<Int>("productId")?.let {productId ->
            viewModelScope.launch {
                productUseCases.getProduct(productId)?.also {product ->
                    currentProductId = product.id
                    _counterState.value = _counterState.value.copy(
                        counter = product.currentCounterValue,
                        goal = product.goalValue,
                        name = product.name,

                    )
                }

                storeSettings.getSettings().collect {settings ->
                    _counterState.value = _counterState.value.copy(
                        counterColor = settings.counterColor,
                        iconsColor = settings.iconsColor,
                        backgroundImage = if (settings.isDefaultBackgroundImage) {
                            "android.resource://com.example.counterjc/${R.drawable.cat_2}"
                        } else {
                            loadBackgroundImage()
                        }
                    )
                }
            }
        }
    }


    fun onEvent(event: CounterEvent) {
        when (event) {
            CounterEvent.ClearCounter -> {
                _counterState.value = _counterState.value.copy(
                    counter = 0
                )
            }
            CounterEvent.ClearGoal -> {
                _counterState.value = _counterState.value.copy(
                    goal = 0
                )
            }
            CounterEvent.Decrease -> {
                if (_counterState.value.counter > 0) {
                    _counterState.value = _counterState.value.copy(
                        counter = _counterState.value.counter - 1
                    )
                }
            }
            CounterEvent.Increase -> {
                _counterState.value = _counterState.value.copy(
                    counter = _counterState.value.counter + 1
                )
            }
            is CounterEvent.SetGoal -> {
                _counterState.value = _counterState.value.copy(
                    goal = event.goal
                )
            }
            CounterEvent.SaveProduct -> {
                viewModelScope.launch {
                    productUseCases.addProduct(
                        Product(
                            name = counterState.value.name,
                            modifiedAt = System.currentTimeMillis(),
                            currentCounterValue = counterState.value.counter,
                            goalValue = counterState.value.goal,
                            id = currentProductId!!
                        )
                    )
                }
            }

            CounterEvent.ShowAchievedGoalDialog -> {
                _counterState.value = _counterState.value.copy(
                    isShowAchievedGoalDialog = true
                )
            }
            CounterEvent.HideAchievedGoalDialog -> {
                _counterState.value = _counterState.value.copy(
                    isShowAchievedGoalDialog = false
                )
            }
            CounterEvent.ShowSetGoalDialog -> {
                _counterState.value = _counterState.value.copy(
                    isShowSetGoalDialog = true
                )
            }
            CounterEvent.HideSetGoalDialog -> {
                _counterState.value = _counterState.value.copy(
                    isShowSetGoalDialog = false
                )
            }
        }
    }

}