package com.intergrupo.gomaps.data.network.service

import com.intergrupo.gomaps.data.model.CountriesModelResponse
import com.intergrupo.gomaps.data.network.apiClient.CountriesApiClient
import com.intergrupo.gomaps.domain.model.GoMapsApiFailed
import com.intergrupo.gomaps.util.FlagConstants.OK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by hans fritz ortega on 20/05/22.
 */
class CountriesService @Inject constructor(private val countriesApiClient: CountriesApiClient) {

    suspend fun getCountries(authToken: String): CountriesModelResponse {
        return withContext(Dispatchers.IO) {
            val reponse = countriesApiClient.getCountries(authToken)
            when (reponse.code()) {
                OK -> CountriesModelResponse(
                    goMapsApiFailed = GoMapsApiFailed(
                        code = reponse.code(),
                        message = reponse.message().toString(),
                        success = true
                    ), countries = reponse.body()!!

                )
                else -> CountriesModelResponse(
                    goMapsApiFailed = GoMapsApiFailed(
                        code = reponse.code(),
                        message = reponse.message().toString(),
                        success = false
                    ), countries = arrayListOf()

                )
            }
        }
    }
}