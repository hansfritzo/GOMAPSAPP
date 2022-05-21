package com.intergrupo.gomaps.data.network.service

import com.intergrupo.gomaps.data.model.StatesModelResponse
import com.intergrupo.gomaps.data.network.apiClient.StatesApiClient
import com.intergrupo.gomaps.domain.model.GoMapsApiFailed
import com.intergrupo.gomaps.util.FlagConstants.OK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by hans fritz ortega on 20/05/22.
 */
class StatesService @Inject constructor(private val statesApiClient: StatesApiClient) {

    suspend fun getStates(authToken: String, countrie: String): StatesModelResponse {
        return withContext(Dispatchers.IO) {
            val reponse = statesApiClient.getStates(authToken, countrie)
            when (reponse.code()) {
                OK -> StatesModelResponse(
                    goMapsApiFailed = GoMapsApiFailed(
                        code = reponse.code(),
                        message = reponse.message().toString(),
                        success = true
                    ), states = reponse.body()!!)

                else -> StatesModelResponse(
                    goMapsApiFailed = GoMapsApiFailed(
                        code = reponse.code(),
                        message = reponse.message().toString(),
                        success = false
                    ), states = arrayListOf())
            }
        }
    }
}