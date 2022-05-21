package com.intergrupo.gomaps.data.database.dao

import androidx.room.*
import com.intergrupo.gomaps.data.database.entities.AuthTokenEntity

/**
 * Created by hans fritz ortega on 20/05/22.
 */
@Dao
interface AuthTokenDao {

    @Query("SELECT * FROM auth_token")
    suspend fun getTokens(): List<AuthTokenEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(authTokenEntity: AuthTokenEntity)

    @Query("DELETE FROM auth_token ")
    suspend fun delete()
}
