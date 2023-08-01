package com.example.counterjc.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CounterViewModel: ViewModel() {

    var counterState by mutableStateOf(CounterState())
        private set

    fun onAction(action: CounterAction) {
        when(action) {
            is CounterAction.Increase -> increaseCounter()
            is CounterAction.Decrease -> decreaseCounter()
            is CounterAction.ClearCounter -> clearCounter()
            is CounterAction.ClearGoal -> clearGoal()
        }
    }

    private fun setGoal(goal: Int) {
        counterState = counterState.copy(
            goal = goal
        )
    }

    private fun clearGoal() {
        counterState = counterState.copy(
            goal = 0
        )
    }

    private fun decreaseCounter() {
        if (counterState.counter > 0) {
            counterState = counterState.copy(
                counter = counterState.counter - 1
            )
        }
    }

    private fun increaseCounter() {
        counterState = counterState.copy(
            counter = counterState.counter + 1
        )
    }

    private fun clearCounter() {
        counterState = counterState.copy(
            counter = 0
        )
    }


}