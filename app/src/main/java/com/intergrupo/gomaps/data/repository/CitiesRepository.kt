package com.intergrupo.gomaps.data.repository

import com.intergrupo.gomaps.data.database.dao.CitiesDao
import com.intergrupo.gomaps.data.database.entities.CitiesEntity
import com.intergrupo.gomaps.data.network.service.CitiesService
import com.intergrupo.gomaps.domain.model.*
import javax.inject.Inject

/**
 * Created by hans fritz ortega on 20/05/22.
 */
class CitiesRepository @Inject constructor(
    private val citiesService: CitiesService,
    private val citiesDao: CitiesDao,
) {

    suspend fun getCitiesFromApi(authToken: String, state: String): CitiesResponse {
        val response = citiesService.getCities(authToken, state)
        return if (response.goMapsApiFailed.success)
            CitiesResponse(
                cities = response.cities.map { it.toDomain() },
                goMapsApiFailed = response.goMapsApiFailed
            )
        else getCitiesDataBase(response.goMapsApiFailed)
    }

    suspend fun getQueryCitiesDataBase(query: String): List<Cities> {
        val response = citiesDao.getCitiesQuery("%$query%")
        return response.map { it.toDomain() }
    }

    suspend fun getCitiesDataBase(goMapsApiFailed: GoMapsApiFailed): CitiesResponse {
        val response = citiesDao.getCities()
        return CitiesResponse(
            goMapsApiFailed = goMapsApiFailed,
            cities = response.map { it.toDomain() })
    }

    suspend fun insertList(citiesEntity: List<CitiesEntity>) {
        citiesDao.insertList(citiesEntity)
    }

    suspend fun clear() {
        citiesDao.delete()
    }
}