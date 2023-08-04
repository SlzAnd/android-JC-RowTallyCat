package com.example.counterjc.logic

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.counterjc.ui.screens.CounterScreen
import com.example.counterjc.ui.screens.HomeScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    val viewModel = viewModel<CounterViewModel>()
    val counterState = viewModel.counterState
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController)
        }

        composable(
            route = Screen.Counter.route
        ) {
            CounterScreen(
                state = counterState,
                onAction = viewModel::onAction,
                navController = navController
            )
        }
    }
}