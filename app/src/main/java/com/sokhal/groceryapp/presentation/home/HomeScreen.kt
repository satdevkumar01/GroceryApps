package com.sokhal.groceryapp.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.sokhal.groceryapp.domain.model.Product
import com.sokhal.groceryapp.presentation.common.components.BottomNavigationBar
import com.sokhal.groceryapp.presentation.common.components.GroceryTopAppBar
import com.sokhal.groceryapp.presentation.common.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    // viewModel: HomeViewModel = hiltViewModel()
) {
    // Sample data for testing
    val products = remember {
        listOf(
            Product(
                id = "1",
                name = "Apple",
                description = "Fresh red apples from local farms",
                price = 2.99,
                imageUrl = "https://example.com/apple.jpg",
                category = "Fruits",
                quantity = 100,
                createdAt = "2023-01-01",
                updatedAt = "2023-01-01"
            ),
            Product(
                id = "2",
                name = "Banana",
                description = "Organic bananas",
                price = 1.99,
                imageUrl = "https://example.com/banana.jpg",
                category = "Fruits",
                quantity = 150,
                createdAt = "2023-01-01",
                updatedAt = "2023-01-01"
            ),
            Product(
                id = "3",
                name = "Milk",
                description = "Fresh whole milk",
                price = 3.49,
                imageUrl = "https://example.com/milk.jpg",
                category = "Dairy",
                quantity = 50,
                createdAt = "2023-01-01",
                updatedAt = "2023-01-01"
            ),
            Product(
                id = "4",
                name = "Bread",
                description = "Freshly baked whole wheat bread",
                price = 2.49,
                imageUrl = "https://example.com/bread.jpg",
                category = "Bakery",
                quantity = 30,
                createdAt = "2023-01-01",
                updatedAt = "2023-01-01"
            )
        )
    }
    
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    Scaffold(
        topBar = {
            GroceryTopAppBar(
                title = "Grocery App",
                navController = navController
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
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "An error occurred",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                ProductGrid(
                    products = products,
                    onProductClick = { productId ->
                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                    }
                )
            }
        }
    }
}

@Composable
fun ProductGrid(
    products: List<Product>,
    onProductClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(products) { product ->
            ProductItem(
                product = product,
                onProductClick = onProductClick
            )
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    onProductClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onProductClick(product.id) }
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            // Product image
            AsyncImage(
                model = product.imageUrl ?: "https://via.placeholder.com/150",
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Product name
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            // Product price
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            
            // Product description
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}