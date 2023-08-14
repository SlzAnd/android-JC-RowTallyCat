package com.example.counterjc.feature_counter.presentation.counter

data class CounterState(
    val name: String = "",
    val counter: Int = 0,
    val goal: Int = -1,
    val isShowAchievedGoalDialog: Boolean = false,
    val isShowSetGoalDialog: Boolean = false
)