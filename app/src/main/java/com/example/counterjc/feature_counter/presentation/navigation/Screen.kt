package com.example.counterjc.feature_counter.presentation.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("home_screen")
    data object Counter: Screen("counter_screen/{productId}") {
        fun passId(id: Int): String {
            return this.route.replace(
                oldValue = "{productId}",
                newValue = id.toString()
            )
        }
    }
    data object Help: Screen("help_screen")
    data object Settings: Screen("settings_screen")
}
