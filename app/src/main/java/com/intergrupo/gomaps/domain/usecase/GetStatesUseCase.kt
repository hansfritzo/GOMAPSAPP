package com.intergrupo.gomaps.domain.usecase

import com.intergrupo.gomaps.data.database.entities.toDataBase
import com.intergrupo.gomaps.data.repository.StatesRepository
import com.intergrupo.gomaps.domain.model.States
import com.intergrupo.gomaps.domain.model.StatesResponse
import javax.inject.Inject

class GetStatesUseCase @Inject constructor(private val statesRepository: StatesRepository) {
    suspend fun getStates(authToken: String, countries: String): StatesResponse {
        val response = statesRepository.getStatesFromApi(authToken, countries)
        return if (response.goMapsApiFailed.success) {
            statesRepository.clear()
            statesRepository.insertList(response.states.map { it.toDataBase() })
            response
        } else statesRepository.getStatesDataBase(response.goMapsApiFailed)
    }

    suspend fun getStatesQuery(query: String): List<States> {
        return statesRepository.getQueryStatesDataBase(query)
    }
}