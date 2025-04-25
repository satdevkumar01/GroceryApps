package com.sokhal.groceryapp.presentation.product.product_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.sokhal.groceryapp.domain.model.Product
import com.sokhal.groceryapp.presentation.common.components.GroceryTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavController,
    productId: String,
    // viewModel: ProductDetailViewModel = hiltViewModel()
) {
    // Sample product data for testing
    val product = remember {
        Product(
            id = productId,
            name = "Sample Product",
            description = "This is a sample product description. It provides detailed information about the product, its features, benefits, and usage instructions.",
            price = 19.99,
            imageUrl = "https://example.com/product.jpg",
            category = "Sample Category",
            quantity = 50,
            createdAt = "2023-01-01",
            updatedAt = "2023-01-01"
        )
    }
    
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    Scaffold(
        topBar = {
            GroceryTopAppBar(
                title = product.name,
                navController = navController,
                showBackButton = true
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "An error occurred",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Product image
                    AsyncImage(
                        model = product.imageUrl ?: "https://via.placeholder.com/400x300",
                        contentDescription = product.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                    
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Product name
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Product price
                        Text(
                            text = "$${product.price}",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Product description
                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = product.description,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Product details
                        Text(
                            text = "Details",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        DetailItem(label = "Category", value = product.category)
                        DetailItem(label = "Quantity Available", value = product.quantity.toString())
                        DetailItem(label = "Product ID", value = product.id)
                    }
                }
            }
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Column(
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}