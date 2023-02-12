package com.alfikri.rizky.core.data.remote.network

import com.alfikri.rizky.core.data.remote.request.LoginRequest
import com.alfikri.rizky.core.data.remote.request.RegisterRequest
import com.alfikri.rizky.core.data.remote.response.BaseNetworkResponse
import com.alfikri.rizky.core.data.remote.response.LoginResponse
import com.alfikri.rizky.core.data.remote.response.StoriesResponse
import com.alfikri.rizky.core.data.remote.response.StoryDetailResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version ApiService, v 0.1 12/26/2022 7:30 AM by Rizky Alfikri Rachmat
 */
interface ApiService {

    @POST("register")
    suspend fun register(@Body request: RegisterRequest): BaseNetworkResponse

    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @Multipart
    @POST("stories")
    suspend fun addStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody? = null,
        @Part("lon") lon: RequestBody? = null
    ): BaseNetworkResponse

    @GET("stories")
    suspend fun getAllStory(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("location") location: Int?,
    ): StoriesResponse

    @GET("stories/{id}")
    suspend fun getDetailStory(@Path("id") id: String): StoryDetailResponse
}