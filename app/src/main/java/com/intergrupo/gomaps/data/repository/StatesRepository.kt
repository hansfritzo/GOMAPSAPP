package com.intergrupo.gomaps.data.repository

import com.intergrupo.gomaps.data.database.dao.StatesDao
import com.intergrupo.gomaps.data.database.entities.StatesEntity
import com.intergrupo.gomaps.data.network.service.StatesService
import com.intergrupo.gomaps.domain.model.*
import javax.inject.Inject

/**
 * Created by hans fritz ortega on 20/05/22.
 */
class StatesRepository @Inject constructor(
    private val statesService: StatesService, private val statesDao: StatesDao,
) {

    suspend fun getStatesFromApi(authToken: String, countrie: String): StatesResponse {
        val response = statesService.getStates(authToken, countrie)
        return if (response.goMapsApiFailed.success)
            StatesResponse(
                states = response.states.map { it.toDomain() },
                goMapsApiFailed = response.goMapsApiFailed
            )
        else getStatesDataBase(response.goMapsApiFailed)
    }

    suspend fun getQueryStatesDataBase(query: String): List<States> {
        val response = statesDao.getStatesQuery("%$query%")
        return response.map { it.toDomain() }

    }

    suspend fun getStatesDataBase(goMapsApiFailed: GoMapsApiFailed): StatesResponse {
        val response = statesDao.getStates()
        return StatesResponse(
            goMapsApiFailed = goMapsApiFailed,
            states = response.map { it.toDomain() })
    }

    suspend fun insertList(statesEntity: List<StatesEntity>) {
        statesDao.insertList(statesEntity)
    }

    suspend fun clear() {
        statesDao.delete()
    }
}