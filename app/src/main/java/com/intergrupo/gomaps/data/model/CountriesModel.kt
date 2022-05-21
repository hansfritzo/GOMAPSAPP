package com.intergrupo.gomaps.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by hans fritz ortega on 20/05/22.
 */
data class CountriesModel(

    @SerializedName("country_name") val countryName: String,
    @SerializedName("country_short_name") val countryShortName: String,
    @SerializedName("country_phone_code") val countryPhoneCode: Int,

)


