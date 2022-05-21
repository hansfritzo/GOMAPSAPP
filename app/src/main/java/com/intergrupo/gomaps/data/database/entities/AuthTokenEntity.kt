package com.intergrupo.gomaps.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.intergrupo.gomaps.domain.model.AuthToken

/**
 * Created by hans fritz ortega on 20/05/22.
 */
@Entity(tableName = "auth_token")
data class AuthTokenEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int=0,
    @ColumnInfo(name = "auth_token") val authToken: String,

)

fun AuthToken.toDataBase() = AuthTokenEntity(
    authToken = authToken
)

