package com.intergrupo.gomaps.data.network.service

import com.intergrupo.gomaps.data.model.CapitalModelResponse
import com.intergrupo.gomaps.data.network.apiClient.CountriesCapitalApiClient
import com.intergrupo.gomaps.domain.model.GoMapsApiFailed
import com.intergrupo.gomaps.util.FlagConstants.OK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by hans fritz ortega on 20/05/22.
 */
class CountrieCapitalService @Inject constructor(private val countriesCapitalApiClient: CountriesCapitalApiClient) {

    suspend fun getCapital(): CapitalModelResponse {
        return withContext(Dispatchers.IO) {
            val reponse = countriesCapitalApiClient.getCapital()
            when (reponse.code()) {
                OK -> CapitalModelResponse(
                    goMapsApiFailed = GoMapsApiFailed(
                        code = reponse.code(),
                        message = reponse.message().toString(),
                        success = true
                    ), capitals = reponse.body()!!.capitals

                )
                else -> CapitalModelResponse(
                    goMapsApiFailed = GoMapsApiFailed(
                        code = reponse.code(),
                        message = reponse.message().toString(),
                        success = false
                    ), capitals = arrayListOf()

                )

            }
        }
    }
}