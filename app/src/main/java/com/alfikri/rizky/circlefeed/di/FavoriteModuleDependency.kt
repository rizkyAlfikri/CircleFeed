package com.alfikri.rizky.circlefeed.di

import com.alfikri.rizky.core.domain.usecase.StoryFavoriteUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version FavoriteModuleDependency, v 0.1 2/10/2023 7:51 PM by Rizky Alfikri Rachmat
 */
@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependency {

    fun provideStoryFavoriteUseCase(): StoryFavoriteUseCase
}