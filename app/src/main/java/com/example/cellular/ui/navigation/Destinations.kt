package com.example.cellular.ui.navigation

sealed class Screen(val route: String, val label: String) {
    data object Dashboard : Screen("dashboard", "Home")
    data object Plans : Screen("plans", "Plans")
    data object Settings : Screen("settings", "Settings")
}
