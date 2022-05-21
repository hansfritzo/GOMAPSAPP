package com.intergrupo.gomaps.data.network.apiClient

import com.intergrupo.gomaps.data.model.CountriesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * Created by hans fritz ortega on 20/05/22.
 */
interface CountriesApiClient {

    @GET("countries")
    suspend fun getCountries(@Header("Authorization")  authToken: String): Response<ArrayList<CountriesModel>>

}