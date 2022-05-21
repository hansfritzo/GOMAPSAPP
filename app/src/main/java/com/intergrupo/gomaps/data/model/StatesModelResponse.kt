package com.intergrupo.gomaps.data.model

import com.intergrupo.gomaps.domain.model.GoMapsApiFailed

/**
 * Created by hans fritz ortega on 10/7/17.
 */
data class StatesModelResponse(
    var states: ArrayList<StatesModel>,
    var goMapsApiFailed: GoMapsApiFailed,

    )