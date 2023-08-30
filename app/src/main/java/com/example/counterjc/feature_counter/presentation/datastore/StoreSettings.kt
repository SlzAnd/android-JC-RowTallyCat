package com.example.counterjc.feature_counter.presentation.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.counterjc.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreSettings(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = "Settings"
        )
        val COUNTER_COLOR_KEY = longPreferencesKey("counter_color")
        val ICONS_COLOR_KEY = longPreferencesKey("icons_color")
        val BACKGROUND_IMAGE_KEY = stringPreferencesKey("background_image")
        val IS_DEFAULT_BACKGROUND_IMAGE = booleanPreferencesKey("is_default_image")
        val COUNTER_OFFSET_X = floatPreferencesKey("counterOffsetX")
        val COUNTER_OFFSET_Y = floatPreferencesKey("counterOffsetY")
    }

    fun getSettings(): Flow<SettingsData> {
        return context.dataStore.data.map { pref ->
            return@map SettingsData(
                counterColor = pref[COUNTER_COLOR_KEY] ?: 0xFFBDBDBD,
                iconsColor = pref[ICONS_COLOR_KEY] ?: 0xFFBDBDBD,
                isDefaultBackgroundImage = pref[IS_DEFAULT_BACKGROUND_IMAGE] ?: true,
                counterOffsetX = pref[COUNTER_OFFSET_X] ?: 0f,
                counterOffsetY = pref[COUNTER_OFFSET_Y] ?: 0f,
            )
        }
    }

    suspend fun saveSettings(settingsData: SettingsData) {
        context.dataStore.edit { preferences ->
            preferences.remove(BACKGROUND_IMAGE_KEY)
        }

        context.dataStore.edit { preferences ->
            preferences[COUNTER_COLOR_KEY] = settingsData.counterColor
            preferences[ICONS_COLOR_KEY] = settingsData.iconsColor
            preferences[IS_DEFAULT_BACKGROUND_IMAGE] = settingsData.isDefaultBackgroundImage
            preferences[COUNTER_OFFSET_X] = settingsData.counterOffsetX
            preferences[COUNTER_OFFSET_Y] = settingsData.counterOffsetY
        }
    }
}