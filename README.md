# Grocery App

## Overview
Grocery App is a modern Android application built with Jetpack Compose that allows users to browse, add, and manage grocery products. The app follows clean architecture principles and uses the latest Android development practices.

## Features
- **Authentication System**
  - User registration
  - Login
  - Forgot password functionality
  - Profile management
- **Product Management**
  - Browse products
  - View product details
  - Add new products
  - Update existing products
  - Delete products
- **User Interface**
  - Splash screen
  - Bottom navigation
  - Responsive design with Material 3

## Tech Stack
- **UI**: Jetpack Compose with Material 3
- **Architecture**: Clean Architecture with MVVM pattern
- **Dependency Injection**: Dagger Hilt
- **Navigation**: Jetpack Compose Navigation
- **Networking**: Retrofit with OkHttp
- **Concurrency**: Kotlin Coroutines and Flow
- **Image Loading**: Coil
- **Local Storage**: SharedPreferences

## Architecture
The application follows Clean Architecture principles with three main layers:

### 1. Presentation Layer
- Contains UI components built with Jetpack Compose
- ViewModels that handle UI logic and state
- Navigation components

### 2. Domain Layer
- Contains business logic
- Use cases that encapsulate specific business rules
- Repository interfaces
- Domain models

### 3. Data Layer
- Implementation of repository interfaces
- Remote data sources (API calls)
- Local data sources (SharedPreferences)
- Data models (DTOs)

## Project Structure
```
app/
├── src/
│   ├── main/
│   │   ├── java/com/sokhal/groceryapp/
│   │   │   ├── data/
│   │   │   │   ├── local/
│   │   │   │   ├── remote/
│   │   │   │   │   ├── api/
│   │   │   │   │   └── dto/
│   │   │   │   └── repository/
│   │   │   ├── di/
│   │   │   ├── domain/
│   │   │   │   ├── model/
│   │   │   │   ├── repository/
│   │   │   │   ├── use_case/
│   │   │   │   └── util/
│   │   │   ├── presentation/
│   │   │   │   ├── auth/
│   │   │   │   ├── common/
│   │   │   │   ├── home/
│   │   │   │   ├── product/
│   │   │   │   ├── profile/
│   │   │   │   └── splash/
│   │   │   └── ui/
│   │   └── res/
│   └── test/
└── build.gradle.kts
```

## Screens
1. **Splash Screen**: Initial loading screen with app logo
2. **Login Screen**: User authentication
3. **Registration Screen**: New user registration
4. **Forgot Password Screen**: Password recovery
5. **Home Screen**: Displays product list with search functionality
6. **Product Detail Screen**: Shows detailed information about a product
7. **Add Product Screen**: Form to add new products
8. **Profile Screen**: User profile management

## API Endpoints
The app communicates with a backend server using the following endpoints:

### Authentication
- **Register**: `POST /auth/register` - Create a new user account
- **Login**: `POST /auth/login` - Authenticate and get a token
- **Forgot Password**: `POST /auth/forgot-password` - Request a password reset token
- **Reset Password**: `POST /auth/reset-password` - Reset password using a token
- **Update User**: `PUT /auth/user` - Update user profile information

### Products
- **Get All Products**: `GET /products` - Retrieve all products
- **Add Product**: `POST /addproducts` - Add a new product
- **Update Product**: `PUT /products/{id}` - Update an existing product
- **Delete Product**: `DELETE /products/{id}` - Delete a product
- **Get Product by ID**: `GET /products/{id}` - Get a specific product

## Setup Instructions
1. Clone the repository
2. Open the project in Android Studio
3. Update the base URL in `NetworkModule.kt` to point to your backend server
4. Build and run the application

## Requirements
- Android Studio Arctic Fox or newer
- Kotlin 1.5.0 or newer
- Android SDK 24 or higher
- Gradle 7.0 or newer

## Future Improvements
- Implement caching for offline mode
- Add unit and UI tests
- Implement product categories and filtering
- Add shopping cart functionality
- Implement push notifications
- Add dark/light theme support
- Localization for multiple languages

## License
[MIT License](LICENSE)