package com.intergrupo.gomaps.domain.usecase

import com.intergrupo.gomaps.data.database.entities.toDataBase
import com.intergrupo.gomaps.data.repository.CitiesRepository
import com.intergrupo.gomaps.domain.model.Cities
import com.intergrupo.gomaps.domain.model.CitiesResponse
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(private val citiesRepository: CitiesRepository) {
    suspend fun getCities(authToken: String, state: String): CitiesResponse {
        val response = citiesRepository.getCitiesFromApi(authToken, state)
        return if (response.goMapsApiFailed.success) {
            citiesRepository.clear()
            citiesRepository.insertList(response.cities.map { it.toDataBase() })
            response
        } else citiesRepository.getCitiesDataBase(response.goMapsApiFailed)
    }

    suspend fun getCitiesQuery(query: String): List<Cities> {
        val response = citiesRepository.getQueryCitiesDataBase(query)
        return if (response.isNotEmpty()) response
        else emptyList()
    }
}