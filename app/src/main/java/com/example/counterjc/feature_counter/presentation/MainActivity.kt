package com.example.counterjc.feature_counter.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.counterjc.feature_counter.presentation.navigation.SetupNavGraph
import com.example.counterjc.ui.theme.CounterJCTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CounterJCTheme {
                navController = rememberNavController()

                SetupNavGraph(
                    navController = navController,
                )
            }
        }
    }
}
