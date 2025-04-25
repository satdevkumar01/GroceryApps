package com.sokhal.groceryapp.presentation.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.sokhal.groceryapp.domain.model.User
import com.sokhal.groceryapp.presentation.common.components.BottomNavigationBar
import com.sokhal.groceryapp.presentation.common.components.GroceryTopAppBar
import com.sokhal.groceryapp.presentation.common.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    // viewModel: ProfileViewModel = hiltViewModel()
) {
    // Sample user data for testing
    val user = remember {
        User(
            id = "1",
            name = "John Doe",
            email = "john.doe@example.com",
            profilePicture = "https://example.com/profile.jpg"
        )
    }
    
    var name by remember { mutableStateOf(user.name) }
    var email by remember { mutableStateOf(user.email) }
    var profilePicture by remember { mutableStateOf(user.profilePicture ?: "") }
    
    var isEditing by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    
    Scaffold(
        topBar = {
            GroceryTopAppBar(
                title = "Profile",
                navController = navController,
                showBackButton = false
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        floatingActionButton = {
            if (!isEditing) {
                FloatingActionButton(
                    onClick = { isEditing = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Profile"
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile picture
                AsyncImage(
                    model = profilePicture.ifBlank { "https://via.placeholder.com/150" },
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                if (isEditing) {
                    // Name field
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        )
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
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
                    
                    // Profile picture URL field
                    OutlinedTextField(
                        value = profilePicture,
                        onValueChange = { profilePicture = it },
                        label = { Text("Profile Picture URL (optional)") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        )
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Update button
                    Button(
                        onClick = {
                            // Implement update profile logic with viewModel
                            // For now, just show a success message and exit editing mode
                            successMessage = "Profile updated successfully!"
                            isEditing = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isLoading && name.isNotBlank() && email.isNotBlank()
                    ) {
                        Text("Update Profile")
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Cancel button
                    Button(
                        onClick = {
                            // Reset fields and exit editing mode
                            name = user.name
                            email = user.email
                            profilePicture = user.profilePicture ?: ""
                            isEditing = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Cancel")
                    }
                } else {
                    // Display user info
                    Text(
                        text = name,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = email,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Logout button
                    Button(
                        onClick = {
                            // Implement logout logic with viewModel
                            // For now, just navigate to login screen
                            navController.navigate(Screen.Login.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Logout")
                    }
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
                
                // Success message
                successMessage?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                }
            }
            
            // Loading indicator
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}