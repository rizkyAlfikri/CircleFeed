package com.alfikri.rizky.story.data.source.remote

import com.alfikri.rizky.core.data.local.entity.StoryEntity
import com.alfikri.rizky.core.data.remote.network.ApiService
import com.alfikri.rizky.core.data.remote.network.NetworkErrorParser
import com.alfikri.rizky.core.data.remote.response.BaseNetworkResponse
import com.alfikri.rizky.core.data.remote.response.StoriesResponse
import com.alfikri.rizky.core.data.remote.response.StoryDetailResponse
import com.alfikri.rizky.core.data.remote.response.StoryResult
import com.alfikri.rizky.story.data.source.StoryDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version RemoteStoryDataSource, v 0.1 12/31/2022 11:16 AM by Rizky Alfikri Rachmat
 */
class RemoteStoryDataSource(private val secureApiService: ApiService) : StoryDataSource {

    override suspend fun getPagingStories(
        page: Int,
        size: Int
    ): StoriesResponse =
        kotlin.runCatching {
            val response = secureApiService.getAllStory(
                page = page, size = size, location = null,
            )
            response
        }.onFailure {
            val error = NetworkErrorParser(it).parse()
            StoriesResponse().apply {
                this.error = true
                this.message = error.message
            }
        }.getOrElse {
            StoriesResponse().apply {
                this.error = true
                this.message = "System Busy"
            }
        }

    override suspend fun getStoriesWithLocation(): Flow<List<StoryResult>> = flow {
        kotlin.runCatching {
            val response = secureApiService.getAllStory(page = 1, size = 10, location = 1)
            emit(response.stories.orEmpty())
        }.onFailure {
            val error = NetworkErrorParser(it).parse()
            StoriesResponse().apply {
                this.error = true
                this.message = error.message
            }
        }
    }

    override fun getStoryDetail(id: String): Flow<StoryDetailResponse> = flow {
        kotlin.runCatching {
            val response = secureApiService.getDetailStory(id)
            emit(response)
        }.onFailure {
            val error = NetworkErrorParser(it).parse()
            emit(StoryDetailResponse().apply {
                this.error = true
                this.message = error.message
            })
        }
    }

    override fun addStory(
        file: MultipartBody.Part,
        description: RequestBody
    ): Flow<BaseNetworkResponse> = flow {
        kotlin.runCatching {
            val response = secureApiService.addStory(file, description)
            emit(response)
        }.onFailure {
            val error = NetworkErrorParser(it).parse()
            emit(BaseNetworkResponse().apply {
                this.error = true
                this.message = error.message
            })
        }
    }

    override suspend fun insertStory(storyEntity: StoryEntity) {
        throw NotImplementedError()
    }

    override suspend fun deleteStory(storyEntity: StoryEntity) {
        throw NotImplementedError()
    }

    override fun getStoryFavorite(id: String): Flow<StoryEntity?> {
        throw NotImplementedError()
    }
}