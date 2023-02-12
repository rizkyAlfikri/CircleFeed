package com.alfikri.rizky.story.data.source

import com.alfikri.rizky.core.data.BaseFactoryDataSource
import com.alfikri.rizky.core.data.DataSource
import com.alfikri.rizky.core.data.local.database.StoryDao
import com.alfikri.rizky.core.data.remote.network.ApiService
import com.alfikri.rizky.story.data.source.local.LocalStoryDataSource
import com.alfikri.rizky.story.data.source.remote.RemoteStoryDataSource
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryFactoryData, v 0.1 12/31/2022 11:15 AM by Rizky Alfikri Rachmat
 */
@Singleton
class StoryFactoryData @Inject constructor(
    private val storyDao: StoryDao,
    @Named("secure_api_service") private val secureApiService: ApiService
) : BaseFactoryDataSource<StoryDataSource>() {

    override fun getSource(source: DataSource): StoryDataSource {
        return when (source) {
            DataSource.LOCALE -> LocalStoryDataSource(storyDao)
            DataSource.REMOTE -> RemoteStoryDataSource(secureApiService)
            else -> throw NotImplementedError()
        }
    }
}