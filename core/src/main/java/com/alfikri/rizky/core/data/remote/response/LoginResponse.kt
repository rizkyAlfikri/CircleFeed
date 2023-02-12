package com.alfikri.rizky.core.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version RegisterResponse, v 0.1 12/26/2022 8:43 AM by Rizky Alfikri Rachmat
 */
data class LoginResponse(
    @SerializedName("loginResult")
    val loginResult: LoginResult? = null
) : BaseNetworkResponse()

data class LoginResult(
    @SerializedName("userId")
    val userId: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("token")
    val token: String? = null
)