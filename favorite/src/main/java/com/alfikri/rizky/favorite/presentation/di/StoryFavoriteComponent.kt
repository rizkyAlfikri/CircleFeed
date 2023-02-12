package com.alfikri.rizky.favorite.presentation.di

import android.content.Context
import com.alfikri.rizky.circlefeed.di.FavoriteModuleDependency
import com.alfikri.rizky.favorite.presentation.StoryFavoriteActivity
import dagger.BindsInstance
import dagger.Component

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryFavoriteComponent, v 0.1 2/10/2023 7:57 PM by Rizky Alfikri Rachmat
 */
@Component(dependencies = [FavoriteModuleDependency::class])
interface StoryFavoriteComponent {

    fun inject(activity: StoryFavoriteActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependency: FavoriteModuleDependency): Builder
        fun build(): StoryFavoriteComponent
    }
}