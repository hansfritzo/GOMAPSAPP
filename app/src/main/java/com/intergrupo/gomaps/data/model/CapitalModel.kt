package com.intergrupo.gomaps.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by hans fritz ortega on 20/05/22.
 */
data class CapitalModel(

    @SerializedName( "capital_name") val capitalName: String,
    @SerializedName("region_name") val regioName: String,
    @SerializedName( "short_name") val shortName: String,
)
