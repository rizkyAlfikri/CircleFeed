package com.alfikri.rizky.core.domain.usecase

import androidx.paging.PagingData
import com.alfikri.rizky.core.domain.model.StoryFavoriteModel
import kotlinx.coroutines.flow.Flow

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryFavoriteUseCase, v 0.1 2/6/2023 6:36 AM by Rizky Alfikri Rachmat
 */
interface StoryFavoriteUseCase {

    fun getStoryFavorite(): Flow<PagingData<StoryFavoriteModel>>
}