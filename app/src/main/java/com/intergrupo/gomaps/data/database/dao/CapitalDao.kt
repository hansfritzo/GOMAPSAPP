package com.intergrupo.gomaps.data.database.dao

import androidx.room.*
import com.intergrupo.gomaps.data.database.entities.CapitalEntity

/**
 * Created by hans fritz ortega on 20/05/22.
 */
@Dao
interface CapitalDao {

    @Query("SELECT * FROM countries_capital")
    suspend fun getCapital(): List<CapitalEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(capitalEntity: List<CapitalEntity>)

    @Query("DELETE FROM countries_capital ")
    suspend fun delete()
}
