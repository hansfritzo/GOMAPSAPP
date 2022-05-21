package com.intergrupo.gomaps.domain.model

/**
 * Created by hans fritz ortega on 20/05/22.
 */
data class AuthTokenResponse(

    var authToken: AuthToken,

    var goMapsApiFailed: GoMapsApiFailed

    )