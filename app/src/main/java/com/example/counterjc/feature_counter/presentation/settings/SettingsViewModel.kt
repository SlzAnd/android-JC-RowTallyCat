package com.example.counterjc.feature_counter.presentation.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.counterjc.R
import com.example.counterjc.feature_counter.domain.use_case.LoadBackgroundImage
import com.example.counterjc.feature_counter.presentation.datastore.SettingsData
import com.example.counterjc.feature_counter.presentation.datastore.StoreSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val storeSettings: StoreSettings,
    private val loadBackgroundImage: LoadBackgroundImage
) :ViewModel() {

    private val _state = mutableStateOf(SettingsState())
    val state: State<SettingsState> = _state
    init {
        viewModelScope.launch {
            storeSettings.getSettings().collect {settings ->
                _state.value = _state.value.copy(
                    counterColor = settings.counterColor,
                    iconsColor = settings.iconsColor,
                    isPickedDefaultBackgroundImage = settings.isDefaultBackgroundImage,
                    backgroundImage = if (settings.isDefaultBackgroundImage) {
                        "android.resource://dusol.apps.rowtallycat/${R.drawable.mountain}"
                    } else {
                        loadBackgroundImage()
                    }
                )
            }
        }
    }

    fun onEvent(event: SettingsEvent) {
        when(event) {
            SettingsEvent.ShowCounterColorPicker -> {
                _state.value = _state.value.copy(
                    isShowCounterColorPicker = true
                )
            }

            SettingsEvent.HideCounterColorPicker -> {
                _state.value = _state.value.copy(
                    isShowCounterColorPicker = false
                )
            }

            SettingsEvent.ShowIconsColorPicker -> {
                _state.value = _state.value.copy(
                    isShowIconsColorPicker = true
                )
            }

            SettingsEvent.HideIconsColorPicker -> {
                _state.value = _state.value.copy(
                    isShowIconsColorPicker = false
                )
            }

            is SettingsEvent.SetBackgroundImage -> {
                _state.value = _state.value.copy(
                    backgroundImage = event.image,
                    isPickedDefaultBackgroundImage = false
                )
            }

            is SettingsEvent.SetCounterColor -> {
                _state.value = _state.value.copy(
                    counterColor = event.hexColor
                )
            }

            is SettingsEvent.SetIconsColor -> {
                _state.value = _state.value.copy(
                    iconsColor = event.hexColor
                )
            }

            SettingsEvent.SaveSettingsChanges -> {
                viewModelScope.launch {
                    storeSettings.saveSettings(
                        SettingsData(
                            counterColor = _state.value.counterColor,
                            iconsColor = _state.value.iconsColor,
                            isDefaultBackgroundImage = _state.value.isPickedDefaultBackgroundImage,
                            counterOffsetX = _state.value.counterOffsetX,
                            counterOffsetY = _state.value.counterOffsetY
                        )
                    )
                }
            }

            is SettingsEvent.SetDefaultBackgroundImage -> {
                _state.value = _state.value.copy(
                    backgroundImage = event.image,
                    isPickedDefaultBackgroundImage = true
                )
            }

            is SettingsEvent.SetCounterOffset -> {
                _state.value = _state.value.copy(
                    counterOffsetX = _state.value.counterOffsetX + event.offsetX,
                    counterOffsetY = _state.value.counterOffsetY + event.offsetY
                )
            }
        }
    }

}