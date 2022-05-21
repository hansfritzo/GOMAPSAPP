package com.intergrupo.gomaps.domain.model

/**
 * Created by hans fritz ortega on 20/05/22.
 */
data class CapitalResponse(

    var capitals: List<Capital>,
    var goMapsApiFailed: GoMapsApiFailed

    )