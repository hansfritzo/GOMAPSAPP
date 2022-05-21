package com.intergrupo.gomaps.data.model

import com.intergrupo.gomaps.domain.model.GoMapsApiFailed

/**
 * Created by hans fritz ortega on 20/05/22.
 */
data class CountriesModelResponse(

    var countries: ArrayList<CountriesModel>,
    var goMapsApiFailed: GoMapsApiFailed

    )