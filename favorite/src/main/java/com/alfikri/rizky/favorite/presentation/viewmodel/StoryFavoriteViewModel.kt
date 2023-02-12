package com.alfikri.rizky.favorite.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alfikri.rizky.core.domain.model.StoryFavoriteModel
import com.alfikri.rizky.core.domain.usecase.StoryFavoriteUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryFavoriteViewModel, v 0.1 2/6/2023 7:06 AM by Rizky Alfikri Rachmat
 */
class StoryFavoriteViewModel @Inject constructor(
    private val storyFavoriteUseCase: StoryFavoriteUseCase
): ViewModel() {
    val storiesState: Flow<PagingData<StoryFavoriteModel>>
        get() = storyFavoriteUseCase.getStoryFavorite().cachedIn(viewModelScope)
}