package com.example.counterjc.logic.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("home_screen")
    data object Counter: Screen("counter_screen")
}
