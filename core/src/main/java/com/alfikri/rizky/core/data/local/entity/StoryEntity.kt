package com.alfikri.rizky.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryEntity, v 0.1 2/6/2023 6:12 AM by Rizky Alfikri Rachmat
 */
@Entity(tableName = "story")
data class StoryEntity(
    @PrimaryKey
    val id: String,
    val name: String? = null,
    val description: String? = null,
    val photoUrl: String? = null,
    val createdAt: String? = null,
    val lat: Double? = null,
    val lon: Double? = null,
)
