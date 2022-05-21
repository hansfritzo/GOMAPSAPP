package com.intergrupo.gomaps.domain.usecase

import com.intergrupo.gomaps.data.database.entities.toDataBase
import com.intergrupo.gomaps.data.repository.CountriesRepository
import com.intergrupo.gomaps.domain.model.CountiresResponse
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(private val countriesRepository: CountriesRepository) {
    suspend fun getCountries(authToken: String): CountiresResponse {
        val response = countriesRepository.getcountriesFromApi(authToken)
        return if (response.goMapsApiFailed.success) {
            countriesRepository.clear()
            countriesRepository.insertList(response.countries.map { it.toDataBase() })
            response
        } else countriesRepository.getCoutriesDataBase(response.goMapsApiFailed)
    }
}