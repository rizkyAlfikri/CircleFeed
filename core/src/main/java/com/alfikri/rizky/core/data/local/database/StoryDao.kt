package com.alfikri.rizky.core.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alfikri.rizky.core.data.local.entity.StoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryDao, v 0.1 2/6/2023 6:16 AM by Rizky Alfikri Rachmat
 */
@Dao
interface StoryDao {
    @Query("SELECT * FROM story")
    fun getAllStories(): PagingSource<Int, StoryEntity>

    @Query("SELECT * FROM story WHERE :id = id")
    fun getStory(id: String): Flow<StoryEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(storyEntity: StoryEntity)

    @Delete
    suspend fun deleteStory(storyEntity: StoryEntity)

    @Query("DELETE FROM story")
    suspend fun deleteAllStories()
}