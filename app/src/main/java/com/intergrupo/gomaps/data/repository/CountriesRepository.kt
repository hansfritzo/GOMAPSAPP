package com.intergrupo.gomaps.data.repository

import com.intergrupo.gomaps.data.database.dao.CountiresDao
import com.intergrupo.gomaps.data.database.entities.CountriesEntity
import com.intergrupo.gomaps.data.network.service.CountriesService
import com.intergrupo.gomaps.domain.model.*
import javax.inject.Inject

/**
 * Created by hans fritz ortega on 20/05/22.
 */
class CountriesRepository @Inject constructor(
    private val countriesService: CountriesService,
    private val countiresDao: CountiresDao,
) {

    suspend fun getcountriesFromApi(authToken: String): CountiresResponse {
        val response = countriesService.getCountries(authToken)
        return if (response.goMapsApiFailed.success)
            CountiresResponse(
                countries = response.countries.map { it.toDomain() },
                goMapsApiFailed = response.goMapsApiFailed
            )
        else CountiresResponse(countries = emptyList(), goMapsApiFailed = response.goMapsApiFailed)
    }

    suspend fun getCoutriesDataBase(goMapsApiFailed: GoMapsApiFailed): CountiresResponse {
        val response = countiresDao.getCountries()
        return CountiresResponse(
            goMapsApiFailed = goMapsApiFailed,
            countries = response.map { it.toDomain() })
    }

    suspend fun insertList(countriesEntity: List<CountriesEntity>) {
        countiresDao.insertList(countriesEntity)
    }

    suspend fun clear() {
        countiresDao.delete()
    }
}