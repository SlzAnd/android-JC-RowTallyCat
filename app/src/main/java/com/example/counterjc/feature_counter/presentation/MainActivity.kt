package com.example.counterjc.feature_counter.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.counterjc.feature_counter.presentation.navigation.MainNavGraph
import com.example.counterjc.ui.theme.CounterJCTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainNavController = rememberNavController()

            CounterJCTheme {
                MainNavGraph(
                    mainNavController = mainNavController,
                )
            }
        }
    }
}
