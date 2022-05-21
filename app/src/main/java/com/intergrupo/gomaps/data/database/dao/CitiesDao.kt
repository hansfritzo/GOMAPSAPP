package com.intergrupo.gomaps.data.database.dao

import androidx.room.*
import com.intergrupo.gomaps.data.database.entities.CitiesEntity

/**
 * Created by hans fritz ortega on 20/05/22.
 */
@Dao
interface CitiesDao {

    @Query("SELECT * FROM cities")
    suspend fun getCities(): List<CitiesEntity>

    @Query("SELECT * FROM cities WHERE city_name LIKE :query  ")
    suspend fun getCitiesQuery(query:String): List<CitiesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(citiesEntity: List<CitiesEntity>)

    @Query("DELETE FROM cities ")
    suspend fun delete()
}
