package com.example.counterjc.feature_counter.presentation.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.provider.Settings.ACTION_DISPLAY_SETTINGS
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.counterjc.R
import com.example.counterjc.feature_counter.presentation.settings.components.ColorPicker
import com.example.counterjc.feature_counter.presentation.settings.components.PreviewScreenItem
import com.example.counterjc.feature_counter.presentation.settings.components.SettingsItem
import com.example.counterjc.feature_counter.presentation.util.CustomTopAppBar
import com.example.counterjc.feature_counter.presentation.util.NavigationDrawer
import kotlinx.coroutines.launch
import java.io.IOException


@SuppressLint("Recycle")
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val state = viewModel.state.value
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            Log.d("URI", uri.toString())
            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            try {
                context.openFileOutput("backgroundImage.jpg", Context.MODE_PRIVATE).use { stream ->
                    if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                        throw IOException("Couldn't save bitmap")
                    }
                }
            }catch (e: IOException) {
                e.printStackTrace()
            }
            viewModel.onEvent(SettingsEvent.SetBackgroundImage(uri.toString()))
            viewModel.onEvent(SettingsEvent.SaveSettingsChanges)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawer(navController = navController)
        }
    ) {
        Scaffold(
            topBar = {
                CustomTopAppBar(
                    icon = Icons.Filled.Menu,
                    iconDescription = "Navigation menu",
                    title = stringResource(id = R.string.settings_page_title)
                ) {
                    scope.launch {
                        viewModel.onEvent(SettingsEvent.SaveSettingsChanges)
                        drawerState.open()
                    }
                }
            }
        ) {values ->


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
            ) {
                SettingsItem(
                    title = stringResource(id = R.string.settings_display_title)
                ) {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(0.7f),
                            text = stringResource(id = R.string.settings_display_description),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        TextButton(onClick = {
                            val i = Intent(ACTION_DISPLAY_SETTINGS)
                            context.startActivity(i)
                        }) {
                            Text(text = "Change")
                        }
                    }
                }

                SettingsItem(
                    title = stringResource(id = R.string.settings_counter_screen_setup_title)
                ) {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        // Left side

                        if (state.isShowCounterColorPicker) {
                            ColorPicker(
                                onEvent = viewModel::onEvent,
                                isCounterColorPicker = true,
                                initialColor = Color(state.counterColor)
                            )
                        } else if (state.isShowIconsColorPicker) {
                            ColorPicker(
                                onEvent = viewModel::onEvent,
                                isCounterColorPicker = false,
                                initialColor = Color(state.iconsColor)
                            )
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Background image:"
                                )
                                TextButton(
                                    onClick = {
                                        pickMedia.launch(
                                            PickVisualMediaRequest(
                                                ActivityResultContracts.PickVisualMedia.ImageOnly
                                            )
                                        )
                                    }
                                ) {
                                    Text(
                                        text = "Pick image"
                                    )
                                }
                                TextButton(
                                    onClick = {
                                        viewModel.onEvent(SettingsEvent.SetDefaultBackgroundImage(
                                            "android.resource://dusol.apps.rowtallycat/${R.drawable.mountain}"
                                        ))
                                        viewModel.onEvent(SettingsEvent.SaveSettingsChanges)
                                    }
                                ) {
                                    Text(
                                        text = "Default Image"
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = "Counter color:"
                                )
                                TextButton(
                                    onClick = {
                                        viewModel.onEvent(SettingsEvent.ShowCounterColorPicker)
                                    }
                                ) {
                                    Text(
                                        text = "Choose color"
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = "Icons color:"
                                )
                                TextButton(
                                    onClick = {
                                        viewModel.onEvent(SettingsEvent.ShowIconsColorPicker)
                                    }
                                ) {
                                    Text(
                                        text = "Choose color"
                                    )
                                }

                            }
                        }

                        // Right side - Preview Screen
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Preview screen")

                            Spacer(modifier = Modifier.height(16.dp))

                            PreviewScreenItem(
                                imageUri = state.backgroundImage,
                                counterColor = Color(state.counterColor),
                                iconsColor = Color(state.iconsColor),
                                context = context,
                                counterOffsetX = state.counterOffsetX,
                                counterOffsetY = state.counterOffsetY,
                                onEvent = viewModel::onEvent
                            )
                        }
                    }
                }
            }
        }
    }
}