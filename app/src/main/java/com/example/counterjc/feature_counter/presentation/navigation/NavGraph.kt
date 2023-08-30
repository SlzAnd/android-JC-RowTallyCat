package com.example.counterjc.feature_counter.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.counterjc.feature_counter.presentation.counter.CounterScreen
import com.example.counterjc.feature_counter.presentation.help.HelpScreen
import com.example.counterjc.feature_counter.presentation.products.ProductsScreen
import com.example.counterjc.feature_counter.presentation.settings.SettingsScreen


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            ProductsScreen(
                navController = navController
            )
        }

        composable(
            route = Screen.Counter.route,
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.IntType
                }
            )
        ) {
                CounterScreen(
                    navController = navController
                )
        }

        composable(
            route = Screen.Help.route
        ) {
            HelpScreen(
                navController = navController
            )
        }

        composable(
            route = Screen.Settings.route
        ) {
            SettingsScreen(
                navController = navController,
            )
        }
    }
}