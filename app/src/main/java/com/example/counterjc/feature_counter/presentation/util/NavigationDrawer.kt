package com.example.counterjc.feature_counter.presentation.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.counterjc.R
import com.example.counterjc.feature_counter.presentation.navigation.Screen

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
        ,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.drawer_header_image),
            contentDescription = null)
    }
}

@Composable
fun NavigationDrawer(
    navController: NavHostController
) {
    ModalDrawerSheet {
        DrawerHeader()

        NavigationDrawerItem(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Go to home screen"
                )
            },
            label = {
                Text(text = stringResource(id = R.string.drawer_menu_item_home))
            },
            selected = false,
            onClick = {
                      navController.navigate(
                          route = Screen.Home.route
                      )
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Go to settings screen"
                )
            },
            label = {
                Text(text = stringResource(id = R.string.drawer_menu_item_settings))
            },
            selected = false,
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Get help"
                )
            },
            label = {
                Text(text = stringResource(id = R.string.drawer_menu_item_help))
            },
            selected = false,
            onClick = {
                      navController.navigate(
                          route = Screen.Help.route
                      )
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}