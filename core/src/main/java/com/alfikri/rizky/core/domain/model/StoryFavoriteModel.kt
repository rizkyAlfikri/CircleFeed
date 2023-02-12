package com.alfikri.rizky.core.domain.model

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryFavoriteModel, v 0.1 2/6/2023 6:36 AM by Rizky Alfikri Rachmat
 */
data class StoryFavoriteModel(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val photoUrl: String? = null,
    val createdAt: String? = null,
    val lat: Double? = null,
    val lon: Double? = null,
)