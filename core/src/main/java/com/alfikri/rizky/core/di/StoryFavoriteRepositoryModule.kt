package com.alfikri.rizky.core.di

import com.alfikri.rizky.core.data.StoryFavoriteRepositoryImpl
import com.alfikri.rizky.core.domain.StoryFavoriteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryFavoriteRepositoryModule, v 0.1 2/10/2023 8:16 PM by Rizky Alfikri Rachmat
 */
@Module(includes = [DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class StoryFavoriteRepositoryModule {

    @Binds
    abstract fun provideStoryFavoriteRepository(storyFavoriteRepositoryImpl: StoryFavoriteRepositoryImpl): StoryFavoriteRepository
}