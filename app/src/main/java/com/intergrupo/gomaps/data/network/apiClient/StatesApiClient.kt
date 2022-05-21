package com.intergrupo.gomaps.data.network.apiClient

import com.intergrupo.gomaps.data.model.StatesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * Created by hans fritz ortega on 20/05/22.
 */
interface StatesApiClient {

    @GET("states/{countrie}")

    suspend fun getStates(
        @Header("Authorization") authToken: String,
        @Path("countrie") countrie: String,
    ): Response<ArrayList<StatesModel>>

}