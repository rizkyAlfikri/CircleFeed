package com.alfikri.rizky.authentications.data.source

import com.alfikri.rizky.authentications.data.source.remote.RemoteAuthenticationDataSource
import com.alfikri.rizky.core.data.BaseFactoryDataSource
import com.alfikri.rizky.core.data.DataSource
import com.alfikri.rizky.core.data.remote.network.ApiService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version AuthenticationFactoryData, v 0.1 12/26/2022 10:39 AM by Rizky Alfikri Rachmat
 */
@Singleton
class AuthenticationFactoryData @Inject constructor(
    private val apiService: ApiService
) : BaseFactoryDataSource<AuthenticationDataSource>() {

    override fun getSource(source: DataSource): AuthenticationDataSource {
        return when (source) {
            DataSource.REMOTE -> RemoteAuthenticationDataSource(apiService)
            else -> throw NotImplementedError()
        }
    }
}

