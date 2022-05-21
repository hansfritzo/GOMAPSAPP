package com.intergrupo.gomaps.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.intergrupo.gomaps.domain.model.States

/**
 * Created by hans fritz ortega on 20/05/22.
 */
@Entity(tableName = "states")
data class StatesEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "state_name") val stateName: String,
)

fun States.toDataBase() = StatesEntity(
    stateName = stateName
)
