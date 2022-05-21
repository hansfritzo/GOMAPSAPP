package com.intergrupo.gomaps.data.database.dao

import androidx.room.*
import com.intergrupo.gomaps.data.database.entities.CountriesEntity

/**
 * Created by hans fritz ortega on 20/05/22.
 */
@Dao
interface CountiresDao {

    @Query("SELECT * FROM countries")
    suspend fun getCountries(): List<CountriesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(authTokenEntity: List<CountriesEntity>)

    @Query("DELETE FROM countries ")
    suspend fun delete()
}
