package com.example.counterjc.feature_counter.presentation.settings

import android.content.Context
import android.content.Intent
import android.provider.Settings.ACTION_DISPLAY_SETTINGS
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.counterjc.R
import com.example.counterjc.feature_counter.presentation.counter.CounterEvent
import com.example.counterjc.feature_counter.presentation.settings.components.PreviewScreenItem
import com.example.counterjc.feature_counter.presentation.settings.components.SettingsItem
import com.example.counterjc.feature_counter.presentation.util.CustomTopAppBar
import com.example.counterjc.feature_counter.presentation.util.NavigationDrawer
import com.example.counterjc.ui.theme.PurpleGrey
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    navController: NavHostController,
    context: Context
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

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
//                horizontalAlignment = Alignment.CenterHorizontally
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
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.5f),
//                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Background image:"
                            )
                            TextButton(
                                onClick = {}
                            ) {
                                Text(
                                    text = "Pick image"
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Counter color:"
                            )
                            TextButton(
                                onClick = {}
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
                                onClick = {}
                            ) {
                                Text(
                                    text = "Choose color"
                                )
                            }
                        }

                        // Right side
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Preview screen")
                            
                            Spacer(modifier = Modifier.height(16.dp))

                            PreviewScreenItem()
                        }
                    }
                }
            }
        }
    }
}