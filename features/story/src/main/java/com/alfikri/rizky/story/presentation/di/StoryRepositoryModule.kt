package com.alfikri.rizky.story.presentation.di

import com.alfikri.rizky.core.di.DatabaseModule
import com.alfikri.rizky.core.di.NetworkModule
import com.alfikri.rizky.story.data.StoryRepositoryImpl
import com.alfikri.rizky.story.domain.StoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryRepositoryModule, v 0.1 12/31/2022 11:59 AM by Rizky Alfikri Rachmat
 */
@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class StoryRepositoryModule {

    @Binds
    abstract fun provideStoryRepository(storyRepositoryImpl: StoryRepositoryImpl): StoryRepository
}