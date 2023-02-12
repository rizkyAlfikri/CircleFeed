package com.alfikri.rizky.core.data.remote.network

import com.alfikri.rizky.core.data.local.utils.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version AuthInterceptor, v 0.1 12/26/2022 8:22 AM by Rizky Alfikri Rachmat
 */
@Singleton
class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        sessionManager.getFromPreference(SessionManager.KEY_TOKEN)?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }

}