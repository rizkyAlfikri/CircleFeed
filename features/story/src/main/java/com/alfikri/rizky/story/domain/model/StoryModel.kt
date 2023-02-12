package com.alfikri.rizky.story.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryModel, v 0.1 12/29/2022 8:44 PM by Rizky Alfikri Rachmat
 */
@Parcelize
data class StoryModel(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val photoUrl: String? = null,
    val createdAt: String? = null,
    val lat: Double? = null,
    val lon: Double? = null,
) : Parcelable
