package com.alfikri.rizky.story.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.alfikri.rizky.story.domain.model.StoryModel
import com.alfikri.rizky.story.domain.model.StoryResultModel
import com.alfikri.rizky.story.domain.model.StoryUserData
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryUseCase, v 0.1 12/31/2022 11:05 AM by Rizky Alfikri Rachmat
 */
interface StoryUseCase {

    fun getPagingStories(): LiveData<PagingData<StoryModel>>

    suspend fun getStories(): Flow<List<StoryModel>>

    fun getStoryDetail(id: String): Flow<StoryResultModel<StoryModel>>

    fun addStory(
        file: MultipartBody.Part,
        description: RequestBody
    ): Flow<StoryResultModel<Nothing>>

    fun getUserData(): StoryUserData

    suspend fun logout()

    suspend fun insertStoryFavorite(storyModel: StoryModel)

    suspend fun deleteStoryFavorite(storyModel: StoryModel)

    fun getStoryFavorite(id: String): Flow<StoryModel?>
}