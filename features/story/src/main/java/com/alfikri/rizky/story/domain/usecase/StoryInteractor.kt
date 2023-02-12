package com.alfikri.rizky.story.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.alfikri.rizky.story.domain.StoryRepository
import com.alfikri.rizky.story.domain.model.StoryModel
import com.alfikri.rizky.story.domain.model.StoryResultModel
import com.alfikri.rizky.story.domain.model.StoryUserData
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryInteractor, v 0.1 12/31/2022 11:05 AM by Rizky Alfikri Rachmat
 */
class StoryInteractor @Inject constructor(private val storyRepository: StoryRepository) :
    StoryUseCase {

    override fun getPagingStories(): LiveData<PagingData<StoryModel>> {
        return storyRepository.getPagingStories()
    }

    override suspend fun getStories(): Flow<List<StoryModel>> {
        return storyRepository.getStories()
    }

    override fun getStoryDetail(id: String): Flow<StoryResultModel<StoryModel>> {
        return storyRepository.getStoryDetail(id)
    }

    override fun addStory(
        file: MultipartBody.Part,
        description: RequestBody
    ): Flow<StoryResultModel<Nothing>> {
        return storyRepository.addStory(file, description)
    }

    override fun getUserData(): StoryUserData {
        return storyRepository.getUserData()
    }

    override suspend fun logout() {
        storyRepository.logout()
    }

    override suspend fun insertStoryFavorite(storyModel: StoryModel) {
        storyRepository.insertStoryFavorite(storyModel)
    }

    override suspend fun deleteStoryFavorite(storyModel: StoryModel) {
        storyRepository.deleteStoryFavorite(storyModel)
    }

    override fun getStoryFavorite(id: String): Flow<StoryModel?> {
        return storyRepository.getStoryFavorite(id)
    }
}