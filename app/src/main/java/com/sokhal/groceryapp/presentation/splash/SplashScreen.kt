package com.sokhal.groceryapp.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sokhal.groceryapp.R
import com.sokhal.groceryapp.presentation.common.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // Launch a coroutine to handle the delay and navigation
    LaunchedEffect(key1 = true) {
        delay(2000) // 2 seconds delay
        navController.navigate(Screen.Login.route) {
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }
    
    // Splash screen UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        // Replace with your app logo
        // Image(
        //     painter = painterResource(id = R.drawable.app_logo),
        //     contentDescription = "App Logo",
        //     modifier = Modifier.size(200.dp)
        // )
        
        // Temporary text instead of logo
        Text(
            text = "Grocery App",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}