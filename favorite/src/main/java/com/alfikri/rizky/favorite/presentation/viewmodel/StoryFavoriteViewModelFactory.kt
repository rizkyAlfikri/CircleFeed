package com.alfikri.rizky.favorite.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alfikri.rizky.core.domain.usecase.StoryFavoriteUseCase
import javax.inject.Inject

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryFavoriteViewModelFactory, v 0.1 2/10/2023 8:00 PM by Rizky Alfikri Rachmat
 */
class StoryFavoriteViewModelFactory @Inject constructor(
    private val storyFavoriteUseCase: StoryFavoriteUseCase
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(StoryFavoriteViewModel::class.java) -> {
                StoryFavoriteViewModel(storyFavoriteUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}