package com.intergrupo.gomaps.domain.model

import com.intergrupo.gomaps.data.database.entities.CapitalEntity
import com.intergrupo.gomaps.data.model.CapitalModel

/**
 * Created by hans fritz ortega on 20/05/22.
 */
data class Capital(

    val capitalName: String,
    val regioName: String,
    val shortName: String,
)

fun CapitalModel.toDomain()=Capital(capitalName, regioName, shortName)
fun CapitalEntity.toDomain()=Capital(capitalName, regioName, shortName)