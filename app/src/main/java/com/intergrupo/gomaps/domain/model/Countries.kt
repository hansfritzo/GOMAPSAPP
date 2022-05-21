package com.intergrupo.gomaps.domain.model

import com.intergrupo.gomaps.data.database.entities.CountriesEntity
import com.intergrupo.gomaps.data.model.CountriesModel
import java.io.Serializable

/**
 * Created by hans fritz ortega on 20/05/22.
 */
data class Countries(
    val countryName: String,
    val countryShortName: String,
    val countryPhoneCode: Int,
) : Serializable

fun CountriesModel.toDomain()=Countries(countryName, countryShortName, countryPhoneCode)
fun CountriesEntity.toDomain()=Countries(countryName, countryShortName, countryPhoneCode)