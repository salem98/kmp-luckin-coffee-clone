package com.luckin.clone.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Menu : Screen("menu")
    data object Order : Screen("order")
    data object Account : Screen("account")
}

enum class BottomNavItem(
    val screen: Screen,
    val label: String,
    val iconFilled: String,
    val iconOutline: String
) {
    HOME(Screen.Home, "Home", "🏠", "🏡"),
    MENU(Screen.Menu, "Menu", "📋", "📄"),
    ORDER(Screen.Order, "Order", "📦", "📦"),
    ACCOUNT(Screen.Account, "Account", "👤", "👤")
}
