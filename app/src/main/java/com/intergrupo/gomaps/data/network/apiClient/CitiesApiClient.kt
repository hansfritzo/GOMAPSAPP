package com.intergrupo.gomaps.data.network.apiClient

import com.intergrupo.gomaps.data.model.CitiesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * Created by hans fritz ortega on 20/05/22.
 */
interface CitiesApiClient {

    @GET("cities/{state}")

    suspend fun getCities(
        @Header("Authorization") authToken: String,
        @Path("state") state: String,
    ): Response<ArrayList<CitiesModel>>

}