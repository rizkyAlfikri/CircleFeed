package com.alfikri.rizky.story.data.source

import com.alfikri.rizky.core.data.local.entity.StoryEntity
import com.alfikri.rizky.core.data.remote.response.BaseNetworkResponse
import com.alfikri.rizky.core.data.remote.response.StoriesResponse
import com.alfikri.rizky.core.data.remote.response.StoryDetailResponse
import com.alfikri.rizky.core.data.remote.response.StoryResult
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryDataSource, v 0.1 12/31/2022 11:15 AM by Rizky Alfikri Rachmat
 */
interface StoryDataSource {

    suspend fun getPagingStories(
        page: Int,
        size: Int
    ): StoriesResponse

    suspend fun getStoriesWithLocation(): Flow<List<StoryResult>>

    fun getStoryDetail(id: String): Flow<StoryDetailResponse>

    fun addStory(
        file: MultipartBody.Part,
        description: RequestBody
    ): Flow<BaseNetworkResponse>

    suspend fun insertStory(storyEntity: StoryEntity)

    suspend fun deleteStory(storyEntity: StoryEntity)

    fun getStoryFavorite(id: String): Flow<StoryEntity?>
}