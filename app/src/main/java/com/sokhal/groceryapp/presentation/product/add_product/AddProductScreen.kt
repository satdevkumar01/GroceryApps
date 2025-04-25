package com.sokhal.groceryapp.presentation.product.add_product

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sokhal.groceryapp.presentation.common.components.BottomNavigationBar
import com.sokhal.groceryapp.presentation.common.components.GroceryTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    navController: NavController,
    // viewModel: AddProductViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    
    Scaffold(
        topBar = {
            GroceryTopAppBar(
                title = "Add Product",
                navController = navController,
                showBackButton = false
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
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
                // Name field
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Product Name") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Description field
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Price field
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    )
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Image URL field
                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("Image URL (optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Category field
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Quantity field
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Quantity") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Submit button
                Button(
                    onClick = {
                        // Implement add product logic with viewModel
                        // For now, just show a success message
                        successMessage = "Product added successfully!"
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading && 
                             name.isNotBlank() && 
                             description.isNotBlank() && 
                             price.isNotBlank() && 
                             category.isNotBlank() && 
                             quantity.isNotBlank()
                ) {
                    Text("Add Product")
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