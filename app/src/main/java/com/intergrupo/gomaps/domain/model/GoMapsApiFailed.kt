package com.intergrupo.gomaps.domain.model

import java.io.Serializable

/**
 * Created by hans fritz ortega on 20/05/22.
 */
data class GoMapsApiFailed(

    val code: Int,
    val message: String,
    val success: Boolean
) : Serializable
