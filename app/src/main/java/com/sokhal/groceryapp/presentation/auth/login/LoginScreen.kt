package com.sokhal.groceryapp.presentation.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sokhal.groceryapp.presentation.common.navigation.Screen

@Composable
fun LoginScreen(
    navController: NavController,
    // viewModel: LoginViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            
            // Email field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Password field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Forgot password link
            Text(
                text = "Forgot Password?",
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { navController.navigate(Screen.ForgotPassword.route) },
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Login button
            Button(
                onClick = {
                    // Implement login logic with viewModel
                    // For now, just navigate to home screen
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading && email.isNotBlank() && password.isNotBlank()
            ) {
                Text("Login")
            }
            
            // Error message
            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Register link
            Text(
                text = "Don't have an account? Register",
                modifier = Modifier.clickable { navController.navigate(Screen.Register.route) },
                color = MaterialTheme.colorScheme.primary
            )
        }
        
        // Loading indicator
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}