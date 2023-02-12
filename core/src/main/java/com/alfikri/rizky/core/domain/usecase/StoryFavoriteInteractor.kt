package com.alfikri.rizky.core.domain.usecase

import androidx.paging.PagingData
import com.alfikri.rizky.core.domain.StoryFavoriteRepository
import com.alfikri.rizky.core.domain.model.StoryFavoriteModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryFavoriteInteractor, v 0.1 2/6/2023 6:36 AM by Rizky Alfikri Rachmat
 */
class StoryFavoriteInteractor @Inject constructor(
    private val storyFavoriteRepository: StoryFavoriteRepository
): StoryFavoriteUseCase {

    override fun getStoryFavorite(): Flow<PagingData<StoryFavoriteModel>> {
        return storyFavoriteRepository.getStoryFavorite()
    }
}