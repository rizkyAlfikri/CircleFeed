package com.alfikri.rizky.core.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version BaseNetworkResponse, v 0.1 12/26/2022 8:39 AM by Rizky Alfikri Rachmat
 */
open class BaseNetworkResponse {

    @SerializedName("error")
    var error: Boolean? = null

    @SerializedName("message")
    var message: String? = null
}