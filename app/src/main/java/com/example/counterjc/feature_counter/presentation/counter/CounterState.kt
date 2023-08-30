package com.example.counterjc.feature_counter.presentation.counter

import com.example.counterjc.R

data class CounterState(
    val name: String = "",
    val counter: Int = 0,
    val goal: Int = -1,
    val isShowAchievedGoalDialog: Boolean = false,
    val isShowSetGoalDialog: Boolean = false,
    val counterColor: Long = 0xFFBDBDBD,
    val iconsColor: Long = 0xFF433c51,
    val backgroundImage: String = "android.resource://com.example.counterjc/${R.drawable.cat_2}",
)