package com.alfikri.rizky.story.presentation.di

import com.alfikri.rizky.story.domain.usecase.StoryInteractor
import com.alfikri.rizky.story.domain.usecase.StoryUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryViewModelModule, v 0.1 12/31/2022 12:01 PM by Rizky Alfikri Rachmat
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class StoryViewModelModule {

    @Binds
    @ViewModelScoped
    abstract fun provideStoryUseCase(storyInteractor: StoryInteractor): StoryUseCase
}