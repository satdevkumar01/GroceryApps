package com.sokhal.groceryapp.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sokhal.groceryapp.presentation.auth.forgot_password.ForgotPasswordScreen
import com.sokhal.groceryapp.presentation.auth.login.LoginScreen
import com.sokhal.groceryapp.presentation.auth.register.RegisterScreen
import com.sokhal.groceryapp.presentation.home.HomeScreen
import com.sokhal.groceryapp.presentation.product.add_product.AddProductScreen
import com.sokhal.groceryapp.presentation.product.product_detail.ProductDetailScreen
import com.sokhal.groceryapp.presentation.product.product_list.ProductListScreen
import com.sokhal.groceryapp.presentation.profile.ProfileScreen
import com.sokhal.groceryapp.presentation.splash.SplashScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Splash Screen
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        // Auth Screens
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(route = Screen.Register.route) {
            RegisterScreen(navController = navController)
        }

        composable(route = Screen.ForgotPassword.route) {
            ForgotPasswordScreen(navController = navController)
        }

        // Main Screens
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(route = Screen.AddProduct.route) {
            AddProductScreen(navController = navController)
        }

        composable(route = Screen.ProductList.route) {
            ProductListScreen(navController = navController)
        }

        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }

        // Detail Screens
        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(navController = navController, productId = productId)
        }
    }
}
