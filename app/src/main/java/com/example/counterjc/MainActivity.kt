package com.example.counterjc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.counterjc.data.ProductDatabase
import com.example.counterjc.logic.ProductViewModel
import com.example.counterjc.logic.navigation.SetupNavGraph
import com.example.counterjc.ui.theme.CounterJCTheme

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ProductDatabase::class.java,
            "products.db"
        ).build()
    }

    private val viewModel by viewModels<ProductViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ProductViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CounterJCTheme {
                navController = rememberNavController()
                val state by viewModel.state.collectAsState()

                SetupNavGraph(
                    state = state,
                    onEvent = viewModel::onEvent,
                    navController = navController
                )
            }
        }
    }
}
