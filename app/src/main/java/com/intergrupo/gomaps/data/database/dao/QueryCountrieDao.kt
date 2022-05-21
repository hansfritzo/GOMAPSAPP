package com.intergrupo.gomaps.data.database.dao

import androidx.room.*
import com.intergrupo.gomaps.data.database.entities.QueryCountrieEntity

/**
 * Created by hans fritz ortega on 20/05/22.
 */
@Dao
interface QueryCountrieDao {

    @Query(
        "SELECT DISTINCT countries.country_name , countries_capital.region_name ," +
                " countries_capital.capital_name, countries_capital.short_name" +
                " FROM countries INNER JOIN countries_capital ON " +
                " countries_capital.short_name= countries.country_short_name"
    )
    suspend fun getQueryCountrie(): List<QueryCountrieEntity>

    @Query(
        "SELECT DISTINCT countries.country_name , countries_capital.region_name ," +
                " countries_capital.capital_name, countries_capital.short_name" +
                " FROM countries INNER JOIN countries_capital ON " +
                " countries_capital.short_name= countries.country_short_name " +
                " WHERE countries.country_name LIKE :search OR countries_capital.region_name  LIKE :search" +
                " OR  countries_capital.capital_name LIKE :search OR countries_capital.short_name LIKE :search"
    )
    suspend fun getsearchQueryCountrie(search: String): List<QueryCountrieEntity>
}
