package com.sokhal.groceryapp.di

import com.sokhal.groceryapp.data.repository.AuthRepositoryImpl
import com.sokhal.groceryapp.data.repository.ProductRepositoryImpl
import com.sokhal.groceryapp.domain.repository.AuthRepository
import com.sokhal.groceryapp.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}