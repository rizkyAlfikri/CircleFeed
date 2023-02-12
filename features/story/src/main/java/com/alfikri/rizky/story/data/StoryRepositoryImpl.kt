package com.alfikri.rizky.story.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.alfikri.rizky.core.data.DataSource
import com.alfikri.rizky.core.data.local.database.StoryDao
import com.alfikri.rizky.core.data.local.entity.StoryEntity
import com.alfikri.rizky.core.data.local.utils.SessionManager
import com.alfikri.rizky.core.data.remote.response.StoryDetailResponse
import com.alfikri.rizky.core.data.remote.response.StoryResult
import com.alfikri.rizky.story.data.source.StoryFactoryData
import com.alfikri.rizky.story.data.source.StoryPagingSource
import com.alfikri.rizky.story.domain.StoryRepository
import com.alfikri.rizky.story.domain.model.StoryModel
import com.alfikri.rizky.story.domain.model.StoryResultModel
import com.alfikri.rizky.story.domain.model.StoryUserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryRepositoryImpl, v 0.1 12/31/2022 11:08 AM by Rizky Alfikri Rachmat
 */
@Singleton
class StoryRepositoryImpl @Inject constructor(
    dataSource: StoryFactoryData,
    private val sessionManager: SessionManager,
    private val storyDao: StoryDao
) : StoryRepository {

    private val localSource by lazy {
        dataSource.getSource(DataSource.LOCALE)
    }

    private val remoteSource by lazy {
        dataSource.getSource(DataSource.REMOTE)
    }

    private var storyModels = listOf<StoryModel>()

    override fun getPagingStories(): LiveData<PagingData<StoryModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                StoryPagingSource(
                    dataSource = remoteSource,
                    onStoryModelCallback = {
                        if (storyModels.isEmpty()) {
                            storyModels = it
                        }
                    }
                )
            }
        ).liveData
    }

    override suspend fun getStories(): Flow<List<StoryModel>> {
        return remoteSource.getStoriesWithLocation().map { data -> data.map { it.toStoryModel() } }
    }

    override fun getStoryDetail(id: String): Flow<StoryResultModel<StoryModel>> {
        return remoteSource.getStoryDetail(id).map { it.toStoryDetailModel() }
    }

    override fun addStory(
        file: MultipartBody.Part,
        description: RequestBody
    ): Flow<StoryResultModel<Nothing>> {
        return remoteSource.addStory(file, description)
            .map { StoryResultModel(error = it.error, message = it.message) }
    }

    override fun getUserData(): StoryUserData {
        return StoryUserData(
            username = sessionManager.getFromPreference(SessionManager.KEY_USERNAME) ?: "Username",
            email = sessionManager.getFromPreference(SessionManager.KEY_EMAIL) ?: "Email"
        )
    }

    override suspend fun logout() {
        sessionManager.logout()
        storyDao.deleteAllStories()
    }

    override suspend fun insertStoryFavorite(storyModel: StoryModel) {
        localSource.insertStory(storyModel.toStoryEntity())
    }

    override suspend fun deleteStoryFavorite(storyModel: StoryModel) {
        localSource.deleteStory(storyModel.toStoryEntity())
    }

    override fun getStoryFavorite(id: String): Flow<StoryModel?> {
        return localSource.getStoryFavorite(id).map { it?.toStoryModel() }
    }

    private fun StoryDetailResponse.toStoryDetailModel(): StoryResultModel<StoryModel> {
        return StoryResultModel<StoryModel>().also {
            it.error = error
            it.message = message
            it.data = story?.toStoryModel()
        }
    }

    private fun StoryResult.toStoryModel(): StoryModel = StoryModel(
        id = id,
        name = name,
        description = description,
        photoUrl = photoUrl,
        createdAt = createdAt,
        lat = lat,
        lon = lon,
    )

    private fun StoryEntity.toStoryModel(): StoryModel = StoryModel (
        id = id,
        name = name,
        description = description,
        photoUrl = photoUrl,
        createdAt = createdAt,
        lat = lat,
        lon = lon,
    )

    private fun StoryModel.toStoryEntity(): StoryEntity = StoryEntity (
        id = id.orEmpty(),
        name = name,
        description = description,
        photoUrl = photoUrl,
        createdAt = createdAt,
        lat = lat,
        lon = lon,
    )
}