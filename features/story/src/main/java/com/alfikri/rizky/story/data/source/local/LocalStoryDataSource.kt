package com.alfikri.rizky.story.data.source.local

import com.alfikri.rizky.core.data.local.database.StoryDao
import com.alfikri.rizky.core.data.local.entity.StoryEntity
import com.alfikri.rizky.core.data.remote.response.BaseNetworkResponse
import com.alfikri.rizky.core.data.remote.response.StoriesResponse
import com.alfikri.rizky.core.data.remote.response.StoryDetailResponse
import com.alfikri.rizky.core.data.remote.response.StoryResult
import com.alfikri.rizky.story.data.source.StoryDataSource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version LocalStoryDataSource, v 0.1 2/8/2023 5:45 AM by Rizky Alfikri Rachmat
 */
class LocalStoryDataSource(private val storyDao: StoryDao) : StoryDataSource {

    override suspend fun getPagingStories(page: Int, size: Int): StoriesResponse {
        throw NotImplementedError()
    }

    override suspend fun getStoriesWithLocation(): Flow<List<StoryResult>> {
        throw NotImplementedError()
    }

    override fun getStoryDetail(id: String): Flow<StoryDetailResponse> {
        throw NotImplementedError()
    }

    override fun addStory(
        file: MultipartBody.Part,
        description: RequestBody
    ): Flow<BaseNetworkResponse> {
        throw NotImplementedError()
    }

    override suspend fun insertStory(storyEntity: StoryEntity) {
        storyDao.insertStory(storyEntity)
    }

    override suspend fun deleteStory(storyEntity: StoryEntity) {
        storyDao.deleteStory(storyEntity)
    }

    override fun getStoryFavorite(id: String): Flow<StoryEntity?> {
        return storyDao.getStory(id)
    }
}