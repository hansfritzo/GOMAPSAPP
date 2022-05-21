package com.intergrupo.gomaps.data.network.service

import com.intergrupo.gomaps.data.model.AuthTokenModel
import com.intergrupo.gomaps.data.model.AuthTokenModelResponse
import com.intergrupo.gomaps.data.network.apiClient.AuthTokenApiClient
import com.intergrupo.gomaps.domain.model.GoMapsApiFailed
import com.intergrupo.gomaps.util.FlagConstants.OK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by hans fritz ortega on 20/05/22.
 */
class AuthTokenService @Inject constructor(private val authTokenApiClient: AuthTokenApiClient) {

    suspend fun getToken(): AuthTokenModelResponse {
        return withContext(Dispatchers.IO) {
            val reponse = authTokenApiClient.getToken()
            when (reponse.code()) {
                OK -> AuthTokenModelResponse(
                    goMapsApiFailed = GoMapsApiFailed(
                        code = reponse.code(),
                        message = reponse.message().toString(),
                        success = true
                    ), authToken = reponse.body()!!

                )

             else -> AuthTokenModelResponse(
                    goMapsApiFailed = GoMapsApiFailed(
                        code = reponse.code(),
                        message = reponse.message().toString(),
                        success = false
                    ), authToken = AuthTokenModel(authToken = "")
                )

            }
        }
    }
}