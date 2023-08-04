package com.example.counterjc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.counterjc.logic.CounterViewModel
import com.example.counterjc.logic.SetupNavGraph
import com.example.counterjc.ui.screens.CounterScreen
import com.example.counterjc.ui.screens.HomeScreen
import com.example.counterjc.ui.theme.CounterJCTheme

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CounterJCTheme {
                navController = rememberNavController()

                SetupNavGraph(navController = navController)

//                CounterScreen(
//                    state = counterState,
//                    onAction = viewModel::onAction,
//                )
//                HomeScreen()
            }
        }
    }
}
