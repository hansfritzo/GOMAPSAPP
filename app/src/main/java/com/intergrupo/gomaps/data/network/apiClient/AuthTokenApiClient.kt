package com.intergrupo.gomaps.data.network.apiClient

import com.intergrupo.gomaps.data.model.AuthTokenModel
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by hans fritz ortega on 20/05/22.
 */
interface AuthTokenApiClient {

    @GET("getaccesstoken")
    suspend fun getToken(): Response<AuthTokenModel>

}