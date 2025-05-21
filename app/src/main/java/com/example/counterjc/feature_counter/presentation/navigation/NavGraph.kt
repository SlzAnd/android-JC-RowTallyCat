package com.example.counterjc.feature_counter.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.counterjc.R
import com.example.counterjc.feature_counter.presentation.counter.CounterScreen
import com.example.counterjc.feature_counter.presentation.help.HelpScreen
import com.example.counterjc.feature_counter.presentation.products.ProductsScreen
import com.example.counterjc.feature_counter.presentation.settings.SettingsScreen
import com.example.counterjc.feature_counter.presentation.util.CustomTopAppBar
import com.example.counterjc.feature_counter.presentation.util.NavItem
import com.example.counterjc.feature_counter.presentation.util.Navbar


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainNavGraph(
    mainNavController: NavHostController,
) {
    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showNavigation = currentRoute != Screen.Counter.route

    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        NavItem(
            label = stringResource(id = R.string.drawer_menu_item_home),
            topBarTitle = stringResource(id = R.string.app_name),
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            navigationRoute = Screen.Home
        ),
        NavItem(
            label = stringResource(id = R.string.drawer_menu_item_settings),
            topBarTitle = stringResource(id = R.string.settings_page_title),
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            navigationRoute = Screen.Settings
        ),
        NavItem(
            label = stringResource(id = R.string.drawer_menu_item_help),
            topBarTitle = stringResource(id = R.string.help_screen_title),
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info,
            navigationRoute = Screen.Help
        )
    )


    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = showNavigation,
                enter = fadeIn(tween(1000)),
                exit = fadeOut(tween(1000))
            )
                {
                CustomTopAppBar(
                    title = items[selectedItem].topBarTitle
                )
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = showNavigation,
                enter = fadeIn(tween(1000)),
                exit = fadeOut(tween(1000))
            ) {
                Navbar(
                    selectedItem = selectedItem,
                    items = items,
                    onItemSelected = {
                        selectedItem = it
                        mainNavController.navigate(
                            items[it].navigationRoute.route
                        )
                    }
                )
            }
        },
    ) {
        NavHost(
            modifier = Modifier.padding(if (showNavigation) it else PaddingValues(0.dp)),
            navController = mainNavController,
            startDestination = Screen.Home.route
        ) {

            composable(
                route = Screen.Home.route,
                enterTransition = {
                    fadeIn(animationSpec = tween(1000))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(1000))
                },
                popExitTransition = {
                    fadeOut(animationSpec = tween(1000))
                },
                popEnterTransition = {
                    fadeIn(animationSpec = tween(1000))
                }
            ) {
                ProductsScreen(
                    mainNavController = mainNavController
                )
            }

            composable(
                route = Screen.Counter.route,
                arguments = listOf(
                    navArgument("productId") {
                        type = NavType.IntType
                    }
                ),
                enterTransition = {
                    slideInVertically(
                        animationSpec = tween(1000)
                    ) + fadeIn(animationSpec = tween(1000))
                },
                exitTransition = {
                    slideOutVertically(
                        animationSpec = tween(1000),
                        targetOffsetY = {fullHeight -> -fullHeight}
                    ) + fadeOut(animationSpec = tween(1000))
                },
                popEnterTransition = {
                    slideInVertically(
                        animationSpec = tween(1000)
                    ) + fadeIn(animationSpec = tween(1000))
                },
                popExitTransition = {
                    slideOutVertically(
                        animationSpec = tween(1000),
                        targetOffsetY = {fullHeight -> -fullHeight}
                    ) + fadeOut(animationSpec = tween(1000))
                }
            ) {
                CounterScreen(
                    navController = mainNavController
                )
            }

            composable(
                route = Screen.Help.route,
                enterTransition = {
                    fadeIn(animationSpec = tween(1000))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(1000))
                },
                popExitTransition = {
                    fadeOut(animationSpec = tween(1000))
                },
                popEnterTransition = {
                    fadeIn(animationSpec = tween(1000))
                }
            ) {
                HelpScreen()
            }

            composable(
                route = Screen.Settings.route,
                enterTransition = {
                    fadeIn(animationSpec = tween(1000))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(1000))
                },
                popExitTransition = {
                    fadeOut(animationSpec = tween(1000))
                },
                popEnterTransition = {
                    fadeIn(animationSpec = tween(1000))
                }
            ) {
                SettingsScreen()
            }
        }
    }
}