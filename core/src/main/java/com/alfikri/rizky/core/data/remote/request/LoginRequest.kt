package com.alfikri.rizky.core.data.remote.request

import com.google.gson.annotations.SerializedName

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version LoginRequest, v 0.1 12/26/2022 9:09 AM by Rizky Alfikri Rachmat
 */
data class LoginRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)