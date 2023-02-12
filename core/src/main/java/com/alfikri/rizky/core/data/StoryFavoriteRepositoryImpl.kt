package com.alfikri.rizky.core.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.alfikri.rizky.core.data.local.entity.StoryEntity
import com.alfikri.rizky.core.data.source.StoryFavoriteFactoryData
import com.alfikri.rizky.core.domain.StoryFavoriteRepository
import com.alfikri.rizky.core.domain.model.StoryFavoriteModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryFavoriteRepositoryImpl @Inject constructor(
    dataSource: StoryFavoriteFactoryData
) : StoryFavoriteRepository {

    private val localSource by lazy {
        dataSource.getSource(DataSource.LOCALE)
    }

    override fun getStoryFavorite(): Flow<PagingData<StoryFavoriteModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                localSource.getFavoriteStories()
            }
        ).flow.map { pagingData ->
            pagingData.map { storyEntity ->
                storyEntity.toStoryFavoriteModel()
            }
        }
    }

    private fun StoryEntity.toStoryFavoriteModel(): StoryFavoriteModel {
        return StoryFavoriteModel(
            id, name, description, photoUrl, createdAt, lat, lon,
        )
    }
}