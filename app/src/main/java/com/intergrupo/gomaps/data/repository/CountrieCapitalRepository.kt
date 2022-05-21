package com.intergrupo.gomaps.data.repository

import com.intergrupo.gomaps.data.database.dao.CapitalDao
import com.intergrupo.gomaps.data.database.entities.CapitalEntity
import com.intergrupo.gomaps.data.network.service.CountrieCapitalService
import com.intergrupo.gomaps.domain.model.*
import javax.inject.Inject

/**
 * Created by hans fritz ortega on 20/05/22.
 */
class CountrieCapitalRepository @Inject constructor(
    private val countrieCapitalService: CountrieCapitalService,
    private val capitalDao: CapitalDao,
) {

    suspend fun getCapitalFromApi(): CapitalResponse {
        val response = countrieCapitalService.getCapital()
        return if (response.goMapsApiFailed.success)
            CapitalResponse(
                capitals = response.capitals.map { it.toDomain() },
                goMapsApiFailed = response.goMapsApiFailed
            )
        else getCoutriesDataBase(response.goMapsApiFailed)
    }

    suspend fun getCoutriesDataBase(goMapsApiFailed: GoMapsApiFailed): CapitalResponse {
        val response = capitalDao.getCapital()
        return CapitalResponse(
            goMapsApiFailed = goMapsApiFailed,
            capitals = response.map { it.toDomain() })
    }

    suspend fun insertList(capitalEntity: List<CapitalEntity>) {
        capitalDao.insertList(capitalEntity)
    }

    suspend fun clear() {
        capitalDao.delete()
    }
}