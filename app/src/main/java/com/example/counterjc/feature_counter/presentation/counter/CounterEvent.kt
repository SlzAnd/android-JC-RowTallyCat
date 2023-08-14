package com.example.counterjc.feature_counter.presentation.counter

sealed class CounterEvent {
    data object Increase: CounterEvent()
    data object Decrease: CounterEvent()
    data object ClearCounter: CounterEvent()
    data object ClearGoal: CounterEvent()
    data class SetGoal(val goal: Int): CounterEvent()
    data object SaveProduct: CounterEvent()
    data object ShowAchievedGoalDialog: CounterEvent()
    data object HideAchievedGoalDialog: CounterEvent()
    data object ShowSetGoalDialog: CounterEvent()
    data object HideSetGoalDialog: CounterEvent()
}
