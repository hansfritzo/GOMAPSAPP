package com.intergrupo.gomaps.data.database.entities

import androidx.room.ColumnInfo
import com.intergrupo.gomaps.domain.model.QueryCountrie

/**
 * Created by hans fritz ortega on 20/05/22.
 */
data class QueryCountrieEntity(

    @ColumnInfo(name = "capital_name") val capitalName: String,
    @ColumnInfo(name = "region_name") val regionName: String,
    @ColumnInfo(name = "country_name") val countryName: String,
    @ColumnInfo(name = "short_name") val shortName: String,

    )

fun QueryCountrie.toDataBase() = QueryCountrieEntity(
    capitalName = capitalName,
    regionName = regionName,
    countryName = countryName,
    shortName = shortName
)
