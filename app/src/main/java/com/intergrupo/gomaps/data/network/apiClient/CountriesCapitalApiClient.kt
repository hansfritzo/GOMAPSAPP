package com.intergrupo.gomaps.data.network.apiClient

import com.intergrupo.gomaps.data.model.CapitalModelResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by hans fritz ortega on 20/05/22.
 */
interface CountriesCapitalApiClient {

    @GET("countriescapital")
    suspend fun getCapital(): Response<CapitalModelResponse>

}