package com.intergrupo.gomaps.domain.model

import com.intergrupo.gomaps.data.database.entities.AuthTokenEntity
import com.intergrupo.gomaps.data.model.AuthTokenModel

/**
 * Created by hans fritz ortega on 20/05/22.
 */
data class AuthToken(val authToken: String)

fun AuthTokenModel.toDomain()=AuthToken(authToken)
fun AuthTokenEntity.toDomain()=AuthToken(authToken)

