package com.example.counterjc.logic

sealed class Screen(val route: String) {
    object Home: Screen("home_screen")
    object Counter: Screen("counter_screen")
}
