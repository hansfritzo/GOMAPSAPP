package com.intergrupo.gomaps.data.database.dao

import androidx.room.*
import com.intergrupo.gomaps.data.database.entities.StatesEntity

/**
 * Created by hans fritz ortega on 20/05/22.
 */
@Dao
interface StatesDao {

    @Query("SELECT * FROM states")
    suspend fun getStates(): List<StatesEntity>

    @Query("SELECT * FROM states WHERE state_name LIKE :query  ")
    suspend fun getStatesQuery(query:String): List<StatesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(authTokenEntity: List<StatesEntity>)

    @Query("DELETE FROM states ")
    suspend fun delete()
}
