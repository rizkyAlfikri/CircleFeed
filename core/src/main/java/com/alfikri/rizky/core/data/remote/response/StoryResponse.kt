package com.alfikri.rizky.core.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version StoryResponse, v 0.1 12/26/2022 8:55 AM by Rizky Alfikri Rachmat
 */
data class StoriesResponse(
    @SerializedName("listStory")
    val stories: List<StoryResult>? = null
) : BaseNetworkResponse()

data class StoryDetailResponse(
    @SerializedName("story")
    val story: StoryResult? = null
) : BaseNetworkResponse()

data class StoryResult(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("photoUrl")
    val photoUrl: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("lat")
    val lat: Double? = null,
    @SerializedName("lon")
    val lon: Double? = null,
)