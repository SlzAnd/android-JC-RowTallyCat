package com.example.counterjc.feature_counter.presentation.settings

import com.example.counterjc.R

data class SettingsState(
    val counterColor: Long = 0xFFBDBDBD,
    val counterOffsetX: Float = 0f,
    val counterOffsetY: Float = 0f,
    val iconsColor: Long = 0xFFBDBDBD,
    val backgroundImage: String = "android.resource://com.example.counterjc/${R.drawable.mountain}",
    val isShowCounterColorPicker: Boolean = false,
    val isShowIconsColorPicker: Boolean = false,
    val isPickedDefaultBackgroundImage: Boolean = true
)
