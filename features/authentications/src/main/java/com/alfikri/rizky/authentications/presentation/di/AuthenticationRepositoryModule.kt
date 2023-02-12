package com.alfikri.rizky.authentications.presentation.di

import com.alfikri.rizky.authentications.data.AuthenticationRepositoryImpl
import com.alfikri.rizky.authentications.domain.AuthenticationRepository
import com.alfikri.rizky.core.di.DatabaseModule
import com.alfikri.rizky.core.di.NetworkModule
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version AuthenticationRepositoryModule, v 0.1 12/26/2022 11:08 AM by Rizky Alfikri Rachmat
 */
@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class AuthenticationRepositoryModule {

    @Binds
    abstract fun provideRepository(authenticationRepositoryImpl: AuthenticationRepositoryImpl): AuthenticationRepository
}