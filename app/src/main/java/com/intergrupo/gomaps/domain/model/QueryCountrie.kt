package com.intergrupo.gomaps.domain.model

import com.intergrupo.gomaps.data.database.entities.QueryCountrieEntity
import java.io.Serializable

/**
 * Created by hans fritz ortega on 20/05/22.
 */
data class QueryCountrie(

    val capitalName: String,
    val regionName: String,
    val countryName: String,
    val shortName: String,

    ): Serializable

fun QueryCountrieEntity.toDomain() = QueryCountrie(capitalName, regionName, countryName, shortName)