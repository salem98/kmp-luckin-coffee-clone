package com.luckin.clone

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.luckin.clone.navigation.BottomNavItem
import com.luckin.clone.navigation.Screen
import com.luckin.clone.ui.components.LuckinBottomNavBar
import com.luckin.clone.ui.screens.*
import com.luckin.clone.ui.theme.LuckinTheme

@Composable
fun App() {
    LuckinTheme(darkTheme = false) {
        val navController = rememberNavController()
        
        LuckinApp(navController = navController)
    }
}

@Composable
fun LuckinApp(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route
    
    Scaffold(
        bottomBar = {
            LuckinBottomNavBar(
                currentRoute = currentRoute,
                onNavigate = { item ->
                    if (currentRoute != item.screen.route) {
                        navController.navigate(item.screen.route) {
                            // Pop up to the start destination to avoid stacking
                            popUpTo(Screen.Home.route) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onProductClick = { productId ->
                        // Navigate to product detail (future implementation)
                    }
                )
            }
            
            composable(Screen.Menu.route) {
                MenuScreen(
                    onProductClick = { productId ->
                        // Navigate to product detail (future implementation)
                    },
                    onCartClick = {
                        // Navigate to cart (future implementation)
                    }
                )
            }
            
            composable(Screen.Order.route) {
                OrderScreen()
            }
            
            composable(Screen.Account.route) {
                AccountScreen()
            }
        }
    }
}
