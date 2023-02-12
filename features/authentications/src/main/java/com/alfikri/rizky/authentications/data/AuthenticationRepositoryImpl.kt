package com.alfikri.rizky.authentications.data

import com.alfikri.rizky.authentications.data.source.AuthenticationFactoryData
import com.alfikri.rizky.authentications.domain.AuthenticationRepository
import com.alfikri.rizky.authentications.domain.model.AuthenticationResultModel
import com.alfikri.rizky.authentications.domain.model.LoginRequestModel
import com.alfikri.rizky.authentications.domain.model.RegisterRequestModel
import com.alfikri.rizky.core.data.DataSource
import com.alfikri.rizky.core.data.local.utils.SessionManager
import com.alfikri.rizky.core.data.remote.request.LoginRequest
import com.alfikri.rizky.core.data.remote.request.RegisterRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version AuthenticationRepositoryImpl, v 0.1 12/26/2022 10:33 AM by Rizky Alfikri Rachmat
 */
@Singleton
class AuthenticationRepositoryImpl @Inject constructor(
    dataSource: AuthenticationFactoryData,
    private val sessionManager: SessionManager
) :
    AuthenticationRepository {

    private val remoteSource by lazy {
        dataSource.getSource(DataSource.REMOTE)
    }

    override fun register(registerRequestModel: RegisterRequestModel): Flow<AuthenticationResultModel<Nothing>> {
        return remoteSource.register(registerRequestModel.toRegisterRequest()).map {
            AuthenticationResultModel<Nothing>().apply {
                error = it.error
                message = it.message
            }
        }
    }

    override fun login(loginRequestModel: LoginRequestModel): Flow<AuthenticationResultModel<Nothing>> {
        return remoteSource.login(loginRequestModel.toLoginRequest()).map {
            it.loginResult?.let { loginResult ->
                sessionManager.saveToPreference(
                    SessionManager.KEY_TOKEN,
                    loginResult.token.orEmpty()
                )
                sessionManager.saveToPreference(
                    SessionManager.KEY_USERNAME,
                    loginResult.name.orEmpty()
                )
                sessionManager.saveToPreference(
                    SessionManager.KEY_EMAIL,
                    loginRequestModel.email
                )
            }

            AuthenticationResultModel<Nothing>().apply {
                error = it.error
                message = it.message
            }
        }
    }

    override fun isLogin(): Flow<Boolean> {
        return flow {
            emit(sessionManager.isLogin())
        }
    }

    private fun RegisterRequestModel.toRegisterRequest() = RegisterRequest(
        name = name,
        email = email,
        password = password
    )

    private fun LoginRequestModel.toLoginRequest() = LoginRequest(
        email = email,
        password = password
    )
}