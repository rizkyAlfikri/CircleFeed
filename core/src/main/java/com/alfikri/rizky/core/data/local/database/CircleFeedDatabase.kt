package com.alfikri.rizky.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alfikri.rizky.core.data.local.entity.StoryEntity

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version CircleFeedDatabase, v 0.1 2/6/2023 6:24 AM by Rizky Alfikri Rachmat
 */
@Database(entities = [StoryEntity::class], version = 1)
abstract class CircleFeedDatabase: RoomDatabase() {
    abstract fun storyDao(): StoryDao
}