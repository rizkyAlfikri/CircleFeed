package com.alfikri.rizky.core.data.source

import com.alfikri.rizky.core.data.BaseFactoryDataSource
import com.alfikri.rizky.core.data.DataSource
import com.alfikri.rizky.core.data.local.database.StoryDao
import com.alfikri.rizky.core.data.source.local.LocalStoryFavoriteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryFavoriteFactoryData @Inject constructor(
    private val storyDao: StoryDao
): BaseFactoryDataSource<StoryFavoriteDataSource>() {

    override fun getSource(source: DataSource): StoryFavoriteDataSource {
        return when(source) {
            DataSource.LOCALE -> LocalStoryFavoriteDataSource(storyDao)
            else -> throw NotImplementedError()
        }
    }
}