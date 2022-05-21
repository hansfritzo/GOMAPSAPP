package com.intergrupo.gomaps.domain.model

import com.intergrupo.gomaps.data.database.entities.StatesEntity
import com.intergrupo.gomaps.data.model.StatesModel
import java.io.Serializable

/**
 * Created by hans fritz ortega on 20/05/22.
 */
data class States(val stateName: String) : Serializable

fun StatesModel.toDomain() = States(stateName)
fun StatesEntity.toDomain() = States(stateName)

