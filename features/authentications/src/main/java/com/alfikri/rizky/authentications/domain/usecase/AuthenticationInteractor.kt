package com.alfikri.rizky.authentications.domain.usecase

import com.alfikri.rizky.authentications.domain.AuthenticationRepository
import com.alfikri.rizky.authentications.domain.model.AuthenticationResultModel
import com.alfikri.rizky.authentications.domain.model.LoginRequestModel
import com.alfikri.rizky.authentications.domain.model.RegisterRequestModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version AuthenticationInteractor, v 0.1 12/26/2022 10:28 AM by Rizky Alfikri Rachmat
 */
class AuthenticationInteractor @Inject constructor(private val authenticationRepository: AuthenticationRepository) :
    AuthenticationUseCase {

    override fun register(registerRequestModel: RegisterRequestModel): Flow<AuthenticationResultModel<Nothing>> {
        return authenticationRepository.register(registerRequestModel)
    }

    override fun login(loginRequestModel: LoginRequestModel): Flow<AuthenticationResultModel<Nothing>> {
        return authenticationRepository.login(loginRequestModel)
    }

    override fun isLogin(): Flow<Boolean> {
        return authenticationRepository.isLogin()
    }
}