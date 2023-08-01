package com.example.counterjc.logic

sealed class CounterAction {
    object Increase: CounterAction()
    object Decrease: CounterAction()
    object ClearCounter: CounterAction()
    object ClearGoal: CounterAction()
}
