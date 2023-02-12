package com.alfikri.rizky.core.data.source.local

import androidx.paging.PagingSource
import com.alfikri.rizky.core.data.local.database.StoryDao
import com.alfikri.rizky.core.data.local.entity.StoryEntity
import com.alfikri.rizky.core.data.source.StoryFavoriteDataSource

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version LocalStoryFavoriteDataSource, v 0.1 2/6/2023 6:42 AM by Rizky Alfikri Rachmat
 */
class LocalStoryFavoriteDataSource(private val storyDao: StoryDao): StoryFavoriteDataSource {

    override fun getFavoriteStories(): PagingSource<Int, StoryEntity> {
        return storyDao.getAllStories()
    }
}