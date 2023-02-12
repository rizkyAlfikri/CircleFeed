package com.alfikri.rizky.authentications.presentation.di

import com.alfikri.rizky.authentications.domain.usecase.AuthenticationInteractor
import com.alfikri.rizky.authentications.domain.usecase.AuthenticationUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version AuthenticationViewModelModule, v 0.1 12/26/2022 11:14 AM by Rizky Alfikri Rachmat
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthenticationViewModelModule {

    @Binds
    @ViewModelScoped
    abstract fun provideAuthenticationUseCase(authenticationInteractor: AuthenticationInteractor): AuthenticationUseCase
}