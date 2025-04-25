package com.sokhal.groceryapp.presentation.common.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sokhal.groceryapp.R
import com.sokhal.groceryapp.presentation.common.navigation.Screen

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: Int
)

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem(
            name = "Home",
            route = Screen.Home.route,
            icon = R.drawable.ic_home
        ),
        BottomNavItem(
            name = "Add",
            route = Screen.AddProduct.route,
            icon = R.drawable.ic_add
        ),
        BottomNavItem(
            name = "Profile",
            route = Screen.Profile.route,
            icon = R.drawable.ic_profile
        )
    )
    
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        
        items.forEach { item ->
            NavigationBarItem(
                icon = { 
                    Icon(
                        imageVector = ImageVector.vectorResource(id = item.icon),
                        contentDescription = item.name
                    )
                },
                label = { Text(text = item.name) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}