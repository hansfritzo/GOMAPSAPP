package com.intergrupo.gomaps.domain.usecase

import com.intergrupo.gomaps.data.database.entities.toDataBase
import com.intergrupo.gomaps.data.repository.AuthtokenRepository
import com.intergrupo.gomaps.domain.model.AuthToken
import com.intergrupo.gomaps.domain.model.AuthTokenResponse
import javax.inject.Inject

class GetAuthTokenUseCase @Inject constructor(private val authtokenRepository: AuthtokenRepository) {
    suspend fun getAuthToken(): AuthTokenResponse {
        val response = authtokenRepository.getAuthtokenFromApi()
        return if (response.goMapsApiFailed.success) {
            authtokenRepository.clear()
            authtokenRepository.insert(response.authToken.toDataBase())
            response
        } else authtokenRepository.getTokenDataBase(response.goMapsApiFailed)
    }

    suspend fun getAuthTokenDataBase(): List<AuthToken> {
        val response = authtokenRepository.getTokenDataBase()
        return if (response.isNotEmpty()) response
        else emptyList()
    }
}