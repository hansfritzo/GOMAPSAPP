package com.intergrupo.gomaps.domain.model

import com.intergrupo.gomaps.data.database.entities.CitiesEntity
import com.intergrupo.gomaps.data.model.CitiesModel

/**
 * Created by hans fritz ortega on 20/05/22.
 */
data class Cities(
    val cityName: String,
)
fun CitiesModel.toDomain()=Cities(cityName)
fun CitiesEntity.toDomain()=Cities(cityName)