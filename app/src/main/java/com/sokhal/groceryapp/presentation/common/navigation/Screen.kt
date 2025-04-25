package com.sokhal.groceryapp.presentation.common.navigation

sealed class Screen(val route: String) {
    // Auth screens
    object Splash : Screen("splash_screen")
    object Login : Screen("login_screen")
    object Register : Screen("register_screen")
    object ForgotPassword : Screen("forgot_password_screen")
    
    // Main screens
    object Home : Screen("home_screen")
    object AddProduct : Screen("add_product_screen")
    object Profile : Screen("profile_screen")
    
    // Detail screens
    object ProductDetail : Screen("product_detail_screen/{productId}") {
        fun createRoute(productId: String) = "product_detail_screen/$productId"
    }
}