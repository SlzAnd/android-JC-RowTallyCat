package com.example.counterjc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.counterjc.logic.CounterViewModel
import com.example.counterjc.ui.screens.CounterScreen
import com.example.counterjc.ui.screens.HomeScreen
import com.example.counterjc.ui.theme.CounterJCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CounterJCTheme {
                val viewModel = viewModel<CounterViewModel>()
                val counterState = viewModel.counterState

//                CounterScreen(
//                    state = counterState,
//                    onAction = viewModel::onAction,
//                )
                HomeScreen()
            }
        }
    }
}
