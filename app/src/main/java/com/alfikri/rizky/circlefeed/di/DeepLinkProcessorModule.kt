package com.alfikri.rizky.circlefeed.di

import com.alfikri.rizky.authentications.presentation.deeplink.AuthenticationDeeplinkProcessor
import com.alfikri.rizky.core.navigation.DeeplinkProcessor
import com.alfikri.rizky.story.presentation.navigation.StoryDeeplinkProcessor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version DeepLinkProcessorModule, v 0.1 12/25/2022 6:10 AM by Rizky Alfikri Rachmat
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DeepLinkProcessorModule {

    @Binds
    @IntoSet
    abstract fun bindAuthenticationProcessors(
        authenticationDeeplinkProcessor: AuthenticationDeeplinkProcessor
    ): DeeplinkProcessor

    @Binds
    @IntoSet
    abstract fun bindStoryProcessors(
        storyDeeplinkProcessor: StoryDeeplinkProcessor
    ): DeeplinkProcessor

}