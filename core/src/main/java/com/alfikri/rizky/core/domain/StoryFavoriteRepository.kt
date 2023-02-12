package com.alfikri.rizky.core.domain

import androidx.paging.PagingData
import com.alfikri.rizky.core.domain.model.StoryFavoriteModel
import kotlinx.coroutines.flow.Flow

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryFavoriteRepository, v 0.1 2/6/2023 6:35 AM by Rizky Alfikri Rachmat
 */
interface StoryFavoriteRepository {

    fun getStoryFavorite(): Flow<PagingData<StoryFavoriteModel>>
}