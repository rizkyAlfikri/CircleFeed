package com.alfikri.rizky.authentications.domain

import com.alfikri.rizky.authentications.domain.model.LoginRequestModel
import com.alfikri.rizky.authentications.domain.model.RegisterRequestModel
import com.alfikri.rizky.authentications.domain.model.AuthenticationResultModel
import kotlinx.coroutines.flow.Flow

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version AuthenticationRepository, v 0.1 12/26/2022 10:20 AM by Rizky Alfikri Rachmat
 */
interface AuthenticationRepository {

    fun register(registerRequestModel: RegisterRequestModel): Flow<AuthenticationResultModel<Nothing>>

    fun login(loginRequestModel: LoginRequestModel): Flow<AuthenticationResultModel<Nothing>>

    fun isLogin(): Flow<Boolean>
}