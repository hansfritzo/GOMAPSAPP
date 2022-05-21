package com.intergrupo.gomaps.data.repository

import com.intergrupo.gomaps.data.database.dao.QueryCountrieDao
import com.intergrupo.gomaps.domain.model.*
import javax.inject.Inject

/**
 * Created by hans fritz ortega on 20/05/22.
 */
class QueryCountyRepository @Inject constructor(private val queryCountrieDao: QueryCountrieDao) {

    suspend fun getQueryCountrieDataBase(): List<QueryCountrie> {
        val response = queryCountrieDao.getQueryCountrie()
        return response.map { it.toDomain() }

    }

    suspend fun getSearchQueryCountrieDataBase(search: String): List<QueryCountrie> {
        val response = queryCountrieDao.getsearchQueryCountrie("%$search%")
        return response.map { it.toDomain() }
    }
}