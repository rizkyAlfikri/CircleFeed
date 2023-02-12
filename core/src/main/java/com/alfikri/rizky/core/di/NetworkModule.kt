package com.alfikri.rizky.core.di

import com.alfikri.rizky.core.data.local.utils.SessionManager
import com.alfikri.rizky.core.data.remote.network.ApiService
import com.alfikri.rizky.core.data.remote.network.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version NetworkModule, v 0.1 12/26/2022 7:22 AM by Rizky Alfikri Rachmat
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://story-api.dicoding.dev/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Named("secure_okhttp")
    fun provideSecureOkHttpClient(sessionManager: SessionManager): OkHttpClient {
        val hostname = "story-api.dicoding.dev"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/7Coq67wu+UKhLuqpfjezOTCK/xSH3Gmd69K9O5anOA4=")
            .build()

        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(sessionManager))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }

    @Provides
    @Named("secure_api_service")
    fun provideSecureApiService(@Named("secure_okhttp") client: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://story-api.dicoding.dev/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}