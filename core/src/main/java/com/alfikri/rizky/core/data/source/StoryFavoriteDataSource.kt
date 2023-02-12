package com.alfikri.rizky.core.data.source

import androidx.paging.PagingSource
import com.alfikri.rizky.core.data.local.entity.StoryEntity

interface StoryFavoriteDataSource {

    fun getFavoriteStories(): PagingSource<Int, StoryEntity>
}