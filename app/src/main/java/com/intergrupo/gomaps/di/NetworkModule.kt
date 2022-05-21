package com.intergrupo.gomaps.di

import GoMapsErrorHandler
import com.intergrupo.gomaps.data.network.apiClient.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by hans fritz ortega on 20/05/22.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @Named("universal")
    fun provideRetrofit(): Retrofit {

        val client = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(GoMapsErrorHandler()).build()

        return Retrofit.Builder()
            .baseUrl("https://www.universal-tutorial.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    @Named("gomaps")
    fun provideGoMapsRetrofit(): Retrofit {

        val client = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(GoMapsErrorHandler()).build()

        return Retrofit.Builder()
            .baseUrl("https://us-central1-appvlbogota-43289.cloudfunctions.net/app/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthTokenApiClientt(@Named("universal") retrofit: Retrofit): AuthTokenApiClient {
        return retrofit.create(AuthTokenApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideCountriesApiClient(@Named("universal") retrofit: Retrofit): CountriesApiClient {
        return retrofit.create(CountriesApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideStatesApiClient(@Named("universal") retrofit: Retrofit): StatesApiClient {
        return retrofit.create(StatesApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideCitiesApiClient(@Named("universal") retrofit: Retrofit): CitiesApiClient {
        return retrofit.create(CitiesApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideCapitalApiClient(@Named("gomaps") retrofit: Retrofit): CountriesCapitalApiClient {
        return retrofit.create(CountriesCapitalApiClient::class.java)
    }

}
