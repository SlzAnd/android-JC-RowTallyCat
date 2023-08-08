package com.example.counterjc.logic.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.counterjc.logic.CounterViewModel
import com.example.counterjc.logic.ProductEvent
import com.example.counterjc.logic.ProductState
import com.example.counterjc.ui.screens.CounterScreen
import com.example.counterjc.ui.screens.HomeScreen


@Composable
fun SetupNavGraph(
    state: ProductState,
    onEvent: (ProductEvent) -> Unit,
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
            HomeScreen(
                state = state,
                onEvent = onEvent,
                navController = navController
            )
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