package com.alfikri.rizky.core.di

import android.content.Context
import androidx.room.Room
import com.alfikri.rizky.core.data.local.database.CircleFeedDatabase
import com.alfikri.rizky.core.data.local.database.StoryDao
import com.alfikri.rizky.core.data.local.utils.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version DatabaseModule, v 0.1 12/26/2022 8:32 AM by Rizky Alfikri Rachmat
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideCircleFeedDatabase(@ApplicationContext appContext: Context): CircleFeedDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("CircleFeed".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            appContext,
            CircleFeedDatabase::class.java,
            "CircleFeedDb"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideStoryDao(circleFeedDatabase: CircleFeedDatabase): StoryDao {
        return circleFeedDatabase.storyDao()
    }

    @Provides
    fun getSessionManager(@ApplicationContext context: Context): SessionManager =
        SessionManager(context)
}