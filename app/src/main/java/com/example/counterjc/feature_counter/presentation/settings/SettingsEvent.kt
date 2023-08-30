package com.example.counterjc.feature_counter.presentation.settings

import androidx.compose.ui.graphics.Color

sealed interface SettingsEvent {
    data object ShowCounterColorPicker: SettingsEvent
    data object HideCounterColorPicker: SettingsEvent
    data object ShowIconsColorPicker: SettingsEvent
    data object HideIconsColorPicker: SettingsEvent
    data class SetCounterColor(val hexColor: Long): SettingsEvent
    data class SetIconsColor(val hexColor: Long): SettingsEvent
    data class SetBackgroundImage(val image: String): SettingsEvent
    data class SetDefaultBackgroundImage(val image: String): SettingsEvent
    data class SetCounterOffset(val offsetX: Float, val offsetY: Float): SettingsEvent
    data object SaveSettingsChanges: SettingsEvent
}