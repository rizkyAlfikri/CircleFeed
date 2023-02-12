package com.alfikri.rizky.circlefeed.di

import android.content.Context
import com.alfikri.rizky.core.domain.StoryFavoriteRepository
import com.alfikri.rizky.core.domain.usecase.StoryFavoriteInteractor
import com.alfikri.rizky.core.domain.usecase.StoryFavoriteUseCase
import com.alfikri.rizky.core.navigation.DeeplinkHandler
import com.alfikri.rizky.core.navigation.DeeplinkProcessor
import com.alfikri.rizky.core.navigation.DefaultDeepLinkHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version AppModule, v 0.1 12/25/2022 6:07 AM by Rizky Alfikri Rachmat
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun providesDefaultDeeplinkHandler(
        processor: Set<@JvmSuppressWildcards DeeplinkProcessor>
    ): DeeplinkHandler = DefaultDeepLinkHandler(processor)

    @Provides
    @Singleton
    fun provideStoryFavoriteUseCase(storyFavoriteRepository: StoryFavoriteRepository): StoryFavoriteUseCase {
        return StoryFavoriteInteractor(storyFavoriteRepository)
    }
}