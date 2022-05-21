package com.intergrupo.gomaps.domain.usecase

import com.intergrupo.gomaps.data.database.entities.toDataBase
import com.intergrupo.gomaps.data.repository.CountrieCapitalRepository
import com.intergrupo.gomaps.domain.model.CapitalResponse
import javax.inject.Inject

class GetCountrieCapitalUseCase @Inject constructor(private val capitalRepository: CountrieCapitalRepository) {
    suspend fun getCapitalFormApi(): CapitalResponse {
        val response = capitalRepository.getCapitalFromApi()
        return if (response.goMapsApiFailed.success) {
            capitalRepository.clear()
            capitalRepository.insertList(response.capitals.map { it.toDataBase() })
            response
        } else capitalRepository.getCoutriesDataBase(response.goMapsApiFailed)

    }

}