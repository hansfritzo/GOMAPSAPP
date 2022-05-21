package com.intergrupo.gomaps.domain.usecase

import com.intergrupo.gomaps.data.repository.QueryCountyRepository
import com.intergrupo.gomaps.domain.model.QueryCountrie
import javax.inject.Inject

class GetCountriesQueryUseCase @Inject constructor(private val queryCountyRepository: QueryCountyRepository) {

    suspend fun getQueryCountries(): List<QueryCountrie> {
        val response = queryCountyRepository.getQueryCountrieDataBase()
        return if (response.isNotEmpty()) response
        else emptyList()
    }

    suspend fun getSearchQueryCountries(search: String): List<QueryCountrie> {
        val response = queryCountyRepository.getSearchQueryCountrieDataBase(search)
        return if (response.isNotEmpty()) response
        else emptyList()
    }
}