package com.intergrupo.gomaps.data.repository

import com.intergrupo.gomaps.data.database.dao.AuthTokenDao
import com.intergrupo.gomaps.data.database.entities.AuthTokenEntity
import com.intergrupo.gomaps.data.network.service.AuthTokenService
import com.intergrupo.gomaps.domain.model.AuthToken
import com.intergrupo.gomaps.domain.model.AuthTokenResponse
import com.intergrupo.gomaps.domain.model.GoMapsApiFailed
import com.intergrupo.gomaps.domain.model.toDomain
import javax.inject.Inject

/**
 * Created by hans fritz ortega on 20/05/22.
 */
class AuthtokenRepository @Inject constructor(
    private val authTokenService: AuthTokenService,
    private val authTokenDao: AuthTokenDao,
) {

    suspend fun getAuthtokenFromApi(): AuthTokenResponse {
        val response = authTokenService.getToken()
        return if (response.goMapsApiFailed.success)
            AuthTokenResponse(
                authToken = response.authToken.toDomain(),
                goMapsApiFailed = response.goMapsApiFailed
            )
        else AuthTokenResponse(authToken = AuthToken(authToken = ""),
            goMapsApiFailed = response.goMapsApiFailed)
    }

    suspend fun getTokenDataBase(): List<AuthToken> {
        val response = authTokenDao.getTokens()
        return response.map { it.toDomain() }
    }

    suspend fun getTokenDataBase(goMapsApiFailed: GoMapsApiFailed): AuthTokenResponse {
        val response = authTokenDao.getTokens()
        return if (response.isNotEmpty()) AuthTokenResponse(goMapsApiFailed = goMapsApiFailed,
            authToken = response[0].toDomain())
        else AuthTokenResponse(goMapsApiFailed = goMapsApiFailed,
            authToken = AuthToken(authToken = ""))
    }

    suspend fun insert(authTokenEntity: AuthTokenEntity) {
        authTokenDao.insert(authTokenEntity)
    }

    suspend fun clear() {
        authTokenDao.delete()
    }
}