package com.alfikri.rizky.authentications.data.source.remote

import com.alfikri.rizky.authentications.data.source.AuthenticationDataSource
import com.alfikri.rizky.core.data.remote.network.ApiService
import com.alfikri.rizky.core.data.remote.network.NetworkErrorParser
import com.alfikri.rizky.core.data.remote.request.LoginRequest
import com.alfikri.rizky.core.data.remote.request.RegisterRequest
import com.alfikri.rizky.core.data.remote.response.BaseNetworkResponse
import com.alfikri.rizky.core.data.remote.response.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version RemoteAuthenticationDataSource, v 0.1 12/26/2022 10:50 AM by Rizky Alfikri Rachmat
 */
class RemoteAuthenticationDataSource(
    private val apiService: ApiService
) : AuthenticationDataSource {

    override fun register(registerRequest: RegisterRequest): Flow<BaseNetworkResponse> = flow {
        kotlin.runCatching {
            val response = apiService.register(registerRequest)
            emit(response)
        }.onFailure {
            val error = NetworkErrorParser(it)
            emit(error)
        }
    }

    override fun login(loginRequest: LoginRequest): Flow<LoginResponse> = flow {
        kotlin.runCatching {
            val response = apiService.login(loginRequest)
            emit(response)
        }.onFailure {
            val error = NetworkErrorParser(it).parse()
            emit(LoginResponse().apply {
                this.error = true
                this.message = error.message
            })
        }
    }
}