package com.alfikri.rizky.core.data.remote.network

import com.alfikri.rizky.core.data.remote.response.BaseNetworkResponse
import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import javax.net.ssl.SSLPeerUnverifiedException

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version BaseNetworkResult, v 0.1 1/6/2023 7:21 PM by Rizky Alfikri Rachmat
 */

class NetworkErrorParser(private val exception: Throwable) : BaseNetworkResponse() {

    fun parse(): BaseNetworkResponse {
        when (exception) {
            is HttpException -> handleHttpException(exception)?.let {
                return it
            }

            is SSLPeerUnverifiedException -> {
                return handleSSLPeerUnverifiedException()
            }
        }

        return BaseNetworkResponse()
    }

    private fun handleHttpException(exception: HttpException): BaseNetworkResponse? {
        val errorResponse = exception.response()
        if (errorResponse != null) {
            return parseError(response = errorResponse)
        }

        return null
    }

    private fun handleSSLPeerUnverifiedException(): BaseNetworkResponse {
        return BaseNetworkResponse().apply {
            message = "System Busy"
        }
    }

    private fun parseError(response: Response<*>): BaseNetworkResponse? {
        val errorBodyString = response.errorBody()?.string() ?: return null
        val networkError = Gson().parseDataFromString(
            data = errorBodyString,
            modelClass = BaseNetworkResponse::class.java
        ) {
            Timber.e(it)
        }

        return when {
            // put any error type in here

            else -> networkError
        }
    }
}

fun <T> Gson.parseDataFromString(
    data: String?,
    modelClass: Class<T>,
    onFailure: ((Throwable) -> Unit)? = null
): T? {
    try {
        return this.fromJson(data, modelClass)
    } catch (exception: Throwable) {
        onFailure?.invoke(exception)
    }
    return null
}

