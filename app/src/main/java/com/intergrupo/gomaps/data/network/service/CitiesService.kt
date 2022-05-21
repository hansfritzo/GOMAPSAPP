package com.intergrupo.gomaps.data.network.service

import com.intergrupo.gomaps.data.model.CitiesModelResponse
import com.intergrupo.gomaps.data.network.apiClient.CitiesApiClient
import com.intergrupo.gomaps.domain.model.GoMapsApiFailed
import com.intergrupo.gomaps.util.FlagConstants.OK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by hans fritz ortega on 20/05/22.
 */
class CitiesService @Inject constructor(private val citiesApiClient: CitiesApiClient) {

    suspend fun getCities(authToken: String, state: String): CitiesModelResponse {
        return withContext(Dispatchers.IO) {
            val reponse = citiesApiClient.getCities(authToken, state)
            when (reponse.code()) {
                OK -> CitiesModelResponse(
                    goMapsApiFailed = GoMapsApiFailed(
                        code = reponse.code(),
                        message = reponse.message().toString(),
                        success = true
                    ), cities = reponse.body()!!

                )
                else -> CitiesModelResponse(
                    goMapsApiFailed = GoMapsApiFailed(
                        code = reponse.code(),
                        message = reponse.message().toString(),
                        success = false
                    ), cities = arrayListOf()
                )
            }
        }
    }
}