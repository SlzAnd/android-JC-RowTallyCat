package com.example.counterjc.feature_counter.presentation.settings.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.counterjc.R
import com.example.counterjc.feature_counter.presentation.settings.SettingsEvent
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.drawColorIndicator
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun ColorPicker(
    onEvent: (SettingsEvent) -> Unit,
    isCounterColorPicker: Boolean,
    initialColor: Color
) {
    val controller = rememberColorPickerController()

    Log.d("Color", initialColor.toString())

    Column(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(end = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 16.dp),
            text = "Color picking..."
        )
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            AlphaTile(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .clip(RoundedCornerShape(6.dp)),
                controller = controller
            )
        }
        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(10.dp),
            controller = controller,
            onColorChanged = {
                if (isCounterColorPicker) {
                    onEvent(SettingsEvent.SetCounterColor(it.hexCode.hexToLong()))
                }
                if (!isCounterColorPicker) {
                    onEvent(SettingsEvent.SetIconsColor(it.hexCode.hexToLong()))
                }
            },
            initialColor = initialColor
        )
        AlphaSlider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(17.dp),
            controller = controller
        )
        BrightnessSlider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(17.dp),
            controller = controller
        )

        Button(onClick = {
            onEvent(SettingsEvent.SaveSettingsChanges)
            if (isCounterColorPicker) {
                onEvent(SettingsEvent.HideCounterColorPicker)
            } else {
                onEvent(SettingsEvent.HideIconsColorPicker)
            }
        }) {
            Text(text = stringResource(id = R.string.positive_button_text))
        }
    }
}