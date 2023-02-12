package com.alfikri.rizky.authentications.data.source

import com.alfikri.rizky.core.data.remote.request.LoginRequest
import com.alfikri.rizky.core.data.remote.request.RegisterRequest
import com.alfikri.rizky.core.data.remote.response.BaseNetworkResponse
import com.alfikri.rizky.core.data.remote.response.LoginResponse
import kotlinx.coroutines.flow.Flow

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version AuthenticationDataSource, v 0.1 12/26/2022 10:41 AM by Rizky Alfikri Rachmat
 */
interface AuthenticationDataSource {

    fun register(registerRequest: RegisterRequest): Flow<BaseNetworkResponse>

    fun login(loginRequest: LoginRequest): Flow<LoginResponse>
}